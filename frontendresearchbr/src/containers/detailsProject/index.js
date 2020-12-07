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
    ];

    Promise.all( requests )
      .then( responses => {
        let user = responses[ 0 ];
        let project = responses[ 1 ];
        let categories = responses[ 2 ];

        if( project ) {
          let ids = project.invoices;
          let aux = [];
          ids.forEach(element => {
            if(typeof element === 'number' ) {
              aux.push(this.researchApi.findInvoiceById(element))
            }
            if(typeof element === 'object' ) {
              aux.push(this.researchApi.findInvoiceById(element.id))
            }
          });
          Promise.all( aux )
            .then( res => {
              if( res ) {
                let auxItems = [];
                res.forEach(element => {
                  let items = element.items;
                  items.forEach( itm => {
                    if(typeof itm === 'number' ) {
                      auxItems.push(this.researchApi.findItemById( itm ) )
                    }
                    if(typeof itm === 'object' ) {
                      auxItems.push(this.researchApi.findItemById( element.id ) )
                    }
                  })
                });
                Promise.all( auxItems )
                  .then( res => {
                    this.setState( state => {
                      return {
                        ...state,
                        items: res,
                      }
                    })
                  })
              }
              this.setState( state => {
                return {
                  ...state,
                  invoices: res,
                }
              })
            })
        }
        
        this.setState( state => {
          return {
            ...state,
            user: user,
            project: project,
            role: this.treatApiData.findUserRole(),
            categories: categories,
          }
        })
      })
      
  }

  checkInvoice() {
    if( this.state.project) {
      let ids = this.state.project.invoices;
      let aux = [];
      ids.forEach(element => {
        if(typeof element === 'number' ) {
          aux.push(this.researchApi.findInvoiceById(element))
        }
      });
      Promise.all( aux )
        .then( res => {
          this.setState( state => {
            return {
              ...state,
              invoices: res,
            }
          })
        })
    }
  }

  saveToCSV( e ) {
    e.preventDefault();
    const { project, categories, items, invoices } = this.state;
    let rows = [];
    if( project ) {
      rows.push( [ 'Title', project.title ] );
      rows.push( [ 'Agency', project.agency ] );
      rows.push( [ 'Project code', project.code ] );
      rows.push( [ 'Start date', this.util.convertDate( project.start ) ] );
      rows.push( [ 'End date', this.util.convertDate( project.end ) ] );
      rows.push( [ 'Total funding', this.util.printBRL( project.total ) ] );
      rows.push( [ 'Outgoing', this.util.printBRL( project.outgoing ) ] );
      rows.push( [ 'Balance', this.util.printBRL( project.total - project.outgoing ) ] );
    }
    if( items && categories ) {
      categories.forEach( category => {
        rows.push( [ 'Item category', category.name ] );
        project.invoices.forEach( invoice => {
          items.forEach (item => {
            if ( item.invoice.id === invoice ) {
              if( category.items.length > 0 ) {
                category.items.forEach(itm => {
                  if( item.id === itm.id ||item.id === itm ) {
                    rows.push( [ 'Item', item.name ] );
                    rows.push( [ 'Value', this.util.printBRL(item.value) ] );
                    rows.push( [ 'Quantity', item.quantity ] );
                    rows.push( [ 'Invoice', item.invoice.code ] );
                  }
                } )
              }
            }
          } )
        } )
      } )
    }
    if( invoices && project.invoices.length > 0 ) {
      rows.push( [ 'Section', 'Invoices' ] );
      project.invoices.forEach( inv => {
        invoices.forEach( invoice => {
          if( inv === invoice.id ){
            rows.push( [ 'Invoice code', invoice.code ] );
            rows.push( [ 'Value', this.util.printBRL( invoice.value ) ] );
          }
        } )
      } )
    }
    
    let csvContent = 'data:text/csv;charset=utf-8,' + rows.map( e => e.join(",") ).join("\n");

    let encodedUri = encodeURI( csvContent );
    let link = document.createElement( 'a' );
    link.setAttribute( 'href', encodedUri);
    link.setAttribute( 'download', `Project_${ project.title }.csv`);
    document.body.appendChild(link);

    link.click();
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
                  <ButtonUi classCss='items-btn' name='Add an invoice' link={ `/newInvoice/${ this.props.match.params.id }` } />
                </div>
                <div className='items'>
                  <ButtonUi classCss='items-btn' name='Add new item to an invoice' link={ `/newItem/${ this.props.match.params.id }` } />
                </div>
                <div className='items'>
                  <ButtonUi classCss='items-btn' name='Save to CSV' methodClick={ e => this.saveToCSV( e ) } />
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
                                        if( item.id === itm.id ||item.id === itm ) {
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
              <div className='items'><p>Please, verify your permissions</p></div>
            </React.Fragment>
          ) }
        </div>
      </React.Fragment>
    )
  }
}