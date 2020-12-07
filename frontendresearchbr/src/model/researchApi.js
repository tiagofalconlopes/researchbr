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

  async findAllCategories() {
    return await axios.get(`${ url }categories/all`, {
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

  async findAllItems() {
    return await axios.get(`${ url }items/all`, {
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

  async findAllInvoices() {
    return await axios.get(`${ url }invoices/all`, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      }
    })
    .then( res  => {
      console.log(res.request.responseText)
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

  async findInvoiceById( id ) {
    return await axios.get(`${ url }invoices/invoice/${ id }`, {
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

  async findItemById( id ) {
    return await axios.get(`${ url }items/item/${ id }`, {
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

  async createANewProject( { usersIds, code, agency, title, description, total, outgoing, start, end } ) {
    return await axios.post(`${ url }projects/new`, { usersIds: usersIds, code: code, agency: agency, title: title, description: description, total: total, outgoing: outgoing, start: start, end: end }, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      } 
    } )
    .then( res  => {
      return res.data;
    })
    .catch( error => {
      console.error( error );
    })
  }

  async createANewItem( { name, value, quantity, description, category, invoice } ) {
    return await axios.post(`${ url }items/new`, { name: name, value: value, quantity: quantity, description: description, category: category, invoice: invoice }, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      } 
    } )
    .then( res  => {
      return res.data;
    })
    .catch( error => {
      console.error( error );
    })
  }

  async createANewInvoice( { project, code, value, date } ) {
    return await axios.post(`${ url }invoices/new`, { project: project, code: code, value: value, date: date }, {
      headers: {
        'Authorization': `Bearer ${ window.localStorage.getItem( 'access_token' ) }`
      } 
    } )
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