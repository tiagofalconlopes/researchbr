import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { ButtonUi, InputTextUi } from '../../components';
import { AiOutlineArrowLeft } from "react-icons/ai";
import ResearchApi from '../../model/researchApi';

import './formUser.css';
import TreatApiData from '../../service/treatApiData';

function FormUser() {
  const [ username, setUsername ] = useState( '' );
  const [ email, setEmail ] = useState( '' );
  const [ password, setPassword ] = useState( '' );
  const [ confirmPassword, setConfirmPassword ] = useState( '' )
  const [ cpf, setCpf]  = useState( '' );
  const [ roleName, setRoleName ] = useState( '' );

  const reasearchApi = new ResearchApi();
  const treatApiData = new TreatApiData();

  const handler = async e => {
    e.preventDefault();
    try {
      if( validatePassword() && username.length > 0 && validateCpf() && email.length > 0 && roleName.length > 0 ) {
        console.log(username, email, password, cpf, roleName, confirmPassword)
        document.getElementById('hide-title1').style.visibility = 'hidden';
        document.getElementById('hide-title3').style.visibility = 'hidden';
        await reasearchApi.createANewUser( { username, email, password, cpf, roleName } );
        await reasearchApi.login(username, password);
        treatApiData.verifyToken();
      } 
    } catch (err) {
      document.getElementById('hide-title1').style.visibility = 'visible';
      document.getElementById('hide-title3').style.visibility = 'visible';
      console.log(err);
    }
  }

  const validatePassword = ()  => {
    if ( password === confirmPassword ) {
      document.getElementById('hide-password').style.visibility = 'hidden';
      return true;
    } else {
      document.getElementById('hide-password').style.visibility = 'visible';
      return false;
    }
  }

  const validateCpf = () => {
    const strCPF = cpf;
    let sum = 0;
    let rest;

    if( strCPF === '00000000000' ) {
      document.getElementById('hide-cpf').style.visibility = 'visible';
      return false;
    } 

    for( let i = 1; i <= 9; i++) {
      sum = sum + parseInt( strCPF.substring( i - 1, i ) ) * ( 11 - i );
    } 
    rest = ( sum * 10 ) % 11;
    if( ( rest === 10 ) || ( rest === 11 ) )  rest = 0;
    if ( rest !== parseInt( strCPF.substring( 9, 10 ) ) ) {
      document.getElementById('hide-cpf').style.visibility = 'visible';
      return false;
    }
    
    sum = 0;
    for( let i = 1; i <= 10; i++) {
      sum = sum + parseInt( strCPF.substring( i - 1, i ) ) * ( 12 - i );
    } 
    rest = ( sum * 10 ) % 11;

    if( ( rest === 10 ) || ( rest === 11 ) )  rest = 0;
    if ( rest !== parseInt( strCPF.substring( 10, 11 ) ) ) {
      document.getElementById('hide-cpf').style.visibility = 'visible';
      return false;
    }
    setCpf(strCPF);
    document.getElementById('hide-cpf').style.visibility = 'hidden';
    return true;
}

  const updateRole = ( index ) => {
    return parseInt( index ) === 1 ? setRoleName( 'PRINCIPAL' ) : parseInt( index ) === 2 ? setRoleName( 'ASSISTANT' ) : null;
  }

  return (
    <React.Fragment>
      <div className='default-container'>
        <header>
          <div className='div-center'>
            <Link to='/login' className='link-std'><AiOutlineArrowLeft className='icon-down' />  Back to login</Link>
          </div>
        </header>
        <form className='form' onSubmit={ handler }>
          <div className='form-container'>
            <div className='div-center'>
              <h1 id='hide-title1'>Something wrong has occurred!</h1>
              <h3 id='hide-title3'>Please, check your data and try again.</h3>
            </div>
            <div className='items'>
              <label className='items-label'>Username</label>
              <InputTextUi placeholder='Username' typeInput='text' methodChange={ e => setUsername( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>E-mail</label>
              <InputTextUi placeholder='email@example.com' typeInput='email' methodChange={ e => setEmail( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>CPF</label>
              <InputTextUi placeholder='CPF - only numbers' typeInput='text' methodChange={ e => setCpf( e.target.value ) } requiredField={ true } />
              <p id='hide-cpf'>The cpf is invalid</p>
            </div>
            <div className='items'>
              <label  className='items-label'>Password</label>
              <InputTextUi placeholder='Set your password here' typeInput='password' methodChange={ e => setPassword( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Confirm password</label>
              <InputTextUi placeholder='Repeat your password' typeInput='password' methodBlur={ e => setConfirmPassword( e.target.value ) } requiredField={ true } />
              <p id='hide-password'>The password confirmation did not match the orignial</p>
            </div>
            <div className='items'>
              <select className='items-select' onChange={ e => e.target.value >= 1 ? updateRole( e.target.value ) : null } required>
               <option value="-1">select a role</option>
               <option value="1">PRINCIPAL</option>
               <option value="2">ASSISTANT</option>
              </select>
            </div>
            <div className='items'>
              <ButtonUi classCss='items-btn' name='Submit register' methodClick={ e => handler( e ) } />
            </div>
          </div>
        </form>
      </div>
    </React.Fragment>
  )
}

export default FormUser;