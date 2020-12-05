import React, { Component } from 'react';
import ResearchApi from '../../model/researchApi';
import TreatApiData from '../../service/treatApiData';

export default class ListProjects extends Component {
  constructor( props ) {
    super( props );
    this.researchApi = new ResearchApi();
    this.treatApiData = new TreatApiData();
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
      <div className='default-container'>
        { user && projects && (
            user.projects !== 'undefined' && user.projects.length > 0 ? (
              projects.map( prj => {
                return user.id === prj.users.id ? <div key={ prj.id }><p>{ prj.title }</p></div> : null 
              })
            ) : <div><p>No projects for this user</p></div>
        ) }
      </div>
    )
  }
}