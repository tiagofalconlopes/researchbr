import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from  'react-router-dom';
import { PrivateRoute } from './components/privateRoute';
import { FormUser, ListProjects, Login } from './containers';


export default class App extends Component {
  
  render() {
    return (
      <div className='App'>
        <Router>
          <Route path='/login' exact component={ Login } />
          <Route path='/newUser' exact component={ FormUser } />
          <PrivateRoute path = '/' exact component = { ListProjects } />
        </Router>
      </div>
    )
  }
}