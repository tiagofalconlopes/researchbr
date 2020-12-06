import React, { Component } from 'react';
import { ButtonUi, HeaderIn } from '../../components';
import ResearchApi from '../../model/researchApi';
import TreatApiData from '../../service/treatApiData';
import Util from '../../util/util';

export default class DetailsProjects extends Component {
  constructor( props ) {
    super( props );
    this.researchApi = new ResearchApi();
    this.treatApiData = new TreatApiData();
    this.util = new Util();
    this.state = {
      user: null,
      project: null,
      role: '',
      categories: [],
      items: [],
      invoices: []
    }
  }

  componentDidMount() {
    const projectId = this.props.match.params.id;
    const requests = [
      this.treatApiData.findUserData(),
      this.researchApi.findProjectById( projectId ),
      this.researchApi.findAllCategories(),
      this.researchApi.findAllItems(),
      this.researchApi.findAllInvoices()
    ];

    Promise.all( requests )
      .then( responses => {
        let user = responses[ 0 ];
        let project = responses[ 1 ];
        let categories = responses[ 2 ];
        let items = responses[ 3 ];
        let invoices = responses[ 4 ];
        
        this.setState( state => {
          return {
            ...state,
            user: user,
            project: project,
            role: this.treatApiData.findUserRole(),
            categories: categories,
            items: items,
            invoices: invoices
          }
        } )
      })
  }

  render() {
    const { user, project, role, categories, items, invoices } = this.state;

    return (
      <React.Fragment>
        <HeaderIn />
        <div className='default-container'>
          {
            role === 'ROLE_PRINCIPAL' ? (
              <React.Fragment>
                <div className='items'>
                  <ButtonUi classCss='items-btn' name='Add an invoice' />
                </div>
                <div className='items'>
                  <ButtonUi classCss='items-btn' name='Add new item to an invoice' />
                </div>
              </React.Fragment>
            ) : null
          }
          { role === 'ROLE_PRINCIPAL' && user && project && items ? (
              <div className='items items-list'>
                <ul className='ul-std'>
                  <li className='li-project li-title'>
                    Title: { project.title }
                  </li>
                  <li className='li-project'>
                    Agency: { project.agency }
                  </li>
                  <li className='li-project'>
                    Code: { project.code }
                  </li>
                  <li className='li-project'>
                    Start in: { this.util.convertDate( project.start ) }
                  </li>
                  <li className='li-project'>
                    Close in: { this.util.convertDate( project.end ) }
                  </li>
                  <li className='li-project'>
                    Total funding: { this.util.printBRL( project.total ) }
                  </li>
                  <li className='li-project'>
                    Expenses: { this.util.printBRL( project.outgoing ) }
                  </li>
                  <li className='li-project'>
                    Balance: { this.util.printBRL( project.total - project.outgoing ) }
                  </li>
                  {
                    items && categories && (
                      categories.map( category => {
                        return (
                          <React.Fragment>
                            <li key={ category.id } className='li-project li-title2'>
                              Item category: { category.name }
                            </li>
                            {
                              project.invoices.map( invoice => {
                                return items.map (item => {
                                  if ( item.invoice.id === invoice ) {
                                    if( category.items.length > 0 ) {
                                      return category.items.map(itm => {
                                        if( item.id === itm.id ) {
                                          return (
                                            <div className='item-detail' key={ item.id }>
                                              <li className='item-detail-li'>
                                                Item: { item.name }
                                              </li>
                                              <li className='item-detail-li'>
                                                Value: { this.util.printBRL(item.value) }
                                              </li>
                                              <li className='item-detail-li'>
                                                Quantity: { item.quantity }
                                              </li>
                                              <li className='item-detail-li'>
                                                Invoice: { item.invoice.code }
                                              </li>
                                            </div>
                                          )
                                        } else {
                                          return null;
                                        }
                                      } )
                                    } else {
                                      return null;
                                    }
                                  } else {
                                    return  null;
                                  }
                                } )
                              } )
                            }
                          </React.Fragment>
                        )
                      } )
                    )
                  }
                  <li className='li-project li-title2'>
                    Section: Invoices
                  </li>
                  {
                    invoices && project.invoices.length > 0 && (
                      project.invoices.map( inv => {
                        return (
                          <React.Fragment>
                            {
                              invoices.map( invoice => {
                                if( inv === invoice.id ){
                                  return (
                                    <div className='item-detail' key={ invoice.id }>
                                      <li className='item-detail-li'>
                                        Code: { invoice.code }
                                      </li>
                                      <li className='item-detail-li'>
                                        Value: { this.util.printBRL( invoice.value ) }
                                      </li>
                                    </div>
                                  )
                                } else {
                                  return null;
                                }
                              } )
                            }
                          </React.Fragment>
                        ) 
                      } )
                    )
                  }
                </ul>
              </div>
          ) : (
            <React.Fragment>
              <HeaderIn />
              <div className='items'><p>Please, verify your permissions</p></div>
            </React.Fragment>
          ) }
        </div>
      </React.Fragment>
    )
  }
}