import axios from 'axios';

const url = 'http://localhost:8080/api/';
const login_url = 'http://localhost:8080/login';

export default class ResearchApi {
  async findUserByUsername( username ) {
    return await axios.get(`${ url }users/user/${ username }`, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      }
    })
    .then( res  => {
      return res.data.result; 
    })
    .catch( error => {
      console.error( error );
    })
  }

  async findAllProjects() {
    return await axios.get(`${ url }projects/all`, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      }
    })
    .then( res  => {
      return res.data;
    })
    .catch( error => {
      console.error( error );
    })
  }

  async findProjectById( id ) {
    return await axios.get(`${ url }projects/project/${ id }`, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      }
    })
    .then( res  => {
      return res.data;
    })
    .catch( error => {
      console.error( error );
    })
  }

  async createANewUser( { username, email, password, cpf, roleName } ) {
    return await axios.post(`${ url }users/new`, { username: username, email: email, password: password, cpf: cpf, roleName: roleName } )
    .then( res  => {
      return res.data;
    })
    .catch( error => {
      console.error( error );
    })
  }

  async login( username, password ) {
    const basicAuth = 'Basic ' + window.btoa('um-cliente:mais-um-cliente');
    
    const config = {
      url: `${login_url}?grant_type=password&username=${ username }&password=${ password }`,
      method: 'post',
      headers: {
        'Authorization': basicAuth,
        'Content-Type': 'application/x-www-form-encoded;charset=UTF-8'
      }
    };
    
    return await axios( config )
                    .then( res => {
                      window.localStorage.setItem( 'access_token', res.data.access_token )
                      return res.data;
                    })
                    .catch( err => console.log( err ) );
  }

}