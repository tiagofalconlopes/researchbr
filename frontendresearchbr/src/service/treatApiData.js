import ResearchApi from "../model/researchApi";

export default class TreatApiData {

  findUserRole = () => {
    const autoridades = this._tokenTreatment();
    return autoridades !== null ? autoridades.authorities[ 0 ] : null;
  }
  
  
  findUserData = async () => {
    const researchApi = new ResearchApi();
    const userData = this._tokenTreatment();

    return userData !== null ? 
    await  researchApi.findUserByUsername( userData.user_name )
                .then( res => res )
                .catch( err => console.log( err ) ) : 
      null;
  }

  verifyToken = () => {
    if( localStorage.getItem( 'access_token' ) ) {
      window.location.replace('http://localhost:3000/')
    }
  }

  _tokenTreatment() {
    if( window.localStorage.getItem( 'access_token' )) {
      const token = window.localStorage.getItem( 'access_token' );
      let jwt = token;
      let jwtData = jwt.split( '.' )[ 1 ];
      let decodedJwtJsonData = window.atob( jwtData );
  
      return JSON.parse(decodedJwtJsonData);
    } else {
      return null;
    }
  }

}