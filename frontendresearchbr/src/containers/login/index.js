import React, { Fragment, useState } from 'react';
import { Link } from 'react-router-dom';
import { ButtonUi, InputTextUi } from '../../components';
import ResearchApi from '../../model/researchApi';
import TreatApiData from '../../service/treatApiData';

export default function Login() {

  const [ username, setUsername ] = useState( '' );
  const [ password, setPassword ] = useState( '' );

  const researchApi = new ResearchApi();
  const treatApiData = new TreatApiData();

  
  const handler = async e => {
    e.preventDefault();
    await researchApi.login( username, password );
    treatApiData.verifyToken();
  }
  
  
  return (
    <Fragment>
      {treatApiData.verifyToken()}
        <div>
          <div className="default-container">
            <div className="login-form">
                <div className='div-center'>
                  <h1 className='div-center-title1'>Login</h1>
                </div>
                <form className='form'  onSubmit={ e => handler( e ) }> 
                  <div className='items'>
                    <label className='items-label'>Username</label>
                    <InputTextUi placeholder='Username' typeInput='text' methodChange={ e => setUsername( e.target.value ) } requiredField={ true } />
                  </div>
                  <div className='items'>
                    <label className='items-label'>Password</label>
                    <InputTextUi placeholder='Set your password here' typeInput='password' methodChange={ e => setPassword( e.target.value ) } requiredField={ true } />
                  </div>
                  <div className='items'>
                    <ButtonUi classCss='items-btn' name='Login' methodClick={ e => handler( e ) } link='/' />
                    <div className='div-center'>
                      <Link className='link-std' to='/newUser' >Register here</Link>
                    </div>
                  </div>
                </form>
            </div>
          </div>
        </div>
    </Fragment>
  )
}
  