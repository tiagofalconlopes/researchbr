import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from  'react-router-dom';
import { PrivateRoute } from './components/privateRoute';
import { DetailsProjects, FormUser, ListProjects, Login } from './containers';


export default class App extends Component {
  
  render() {
    return (
      <div className='App'>
        <Router>
          <Route path='/login' exact component={ Login } />
          <Route path='/newUser' exact component={ FormUser } />
          <PrivateRoute path = '/' exact component = { ListProjects } />
          <PrivateRoute path='/project/:id' exact component={ DetailsProjects } />
        </Router>
      </div>
    )
  }
}