import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { ButtonUi, HeaderIn } from '../../components';
import ResearchApi from '../../model/researchApi';
import TreatApiData from '../../service/treatApiData';
import Util from '../../util/util';

export default class ListProjects extends Component {
  constructor( props ) {
    super( props );
    this.researchApi = new ResearchApi();
    this.treatApiData = new TreatApiData();
    this.util = new Util();
    this.state = {
      user: null,
      projects: [],
      role: ''
    }
  }

  componentDidMount() {
    const requests = [
      this.treatApiData.findUserData(),
      this.researchApi.findAllProjects()
    ];

    Promise.all( requests )
      .then( responses => {
        let user = responses[ 0 ];
        let projects = responses[ 1 ];
        
        this.setState( state => {
          return {
            ...state,
            user: user,
            projects: projects,
            role: this.treatApiData.findUserRole()
          }
        } )
      })
  }

  render() {
    const { user, projects, role } = this.state;

    return (
      <React.Fragment>
        <HeaderIn />
        <div className='default-container'>
          {
            role === 'ROLE_PRINCIPAL' ? (
              <div className='items'>
                <ButtonUi classCss='items-btn' name='Register new project' />
              </div>
            ) : null
          }
          { user && projects && (
              user.projects !== 'undefined' && user.projects.length > 0 ? (
                projects.map( prj => {
                  return prj.users.map( usr => {
                    return user.id === usr.id ? 
                      (
                        <Link className='prj-link' to={ `/project/${ prj.id }` }>
                          <div key={ usr.id } className='items items-list'>
                            <ul className='ul-std'>
                              <li className='li-project li-title'>
                                { prj.title }
                              </li>
                              <li className='li-project'>
                                Agency: { prj.agency }
                              </li>
                              <li className='li-project'>
                                Code: { prj.code }
                              </li>
                              <li className='li-project'>
                                Start in: { this.util.convertDate( prj.start ) }
                              </li>
                              <li className='li-project'>
                                Close in: { this.util.convertDate( prj.end ) }
                              </li>
                              <li className='li-project'>
                                Balance: { this.util.printBRL( prj.total - prj.outgoing ) }
                              </li>
                            </ul>
                          </div>
                        </Link>
                      ) : null 
                  } )
                } )
              ) : <div><p>No projects for this user</p></div>
          ) }
        </div>
      </React.Fragment>
    )
  }
}