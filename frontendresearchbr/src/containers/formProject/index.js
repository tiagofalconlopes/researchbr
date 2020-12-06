import React, { useState } from 'react';
import { ButtonUi, HeaderIn, InputTextUi } from '../../components';
import ResearchApi from '../../model/researchApi';

import TreatApiData from '../../service/treatApiData';

function FormProject() {
  const [ code, setCode ] = useState( '' );
  const [ agency, setAgency ] = useState( '' );
  const [ title, setTitle ] = useState( '' );
  const [ description, setDescription ] = useState( '' );
  const [ total, setTotal ] = useState( 0.00 )
  const [ outgoing, setOutgoing]  = useState( 0.00 );
  const [ start, setStart ] = useState();
  const [ end, setEnd ] = useState();
  const [ usersIds, setUsersIds ] = useState([]);
  const [ user, setUser ] = useState();

  const reasearchApi = new ResearchApi();
  const treatApiData = new TreatApiData();

  const handler = async e => {
    e.preventDefault();
    try {
      setUsersIds( [ user.id ] );
      if( usersIds.length > 0 && code && agency && title && total && outgoing + 1 && start && end && description ) {
        console.log(usersIds)
        document.getElementById('hide-title1').style.visibility = 'hidden';
        document.getElementById('hide-title3').style.visibility = 'hidden';
        await reasearchApi.createANewProject( { usersIds, code, agency, title, description, total, outgoing, start, end } );
        treatApiData.verifyToken();
      } 
    } catch (err) {
      document.getElementById('hide-title1').style.visibility = 'visible';
      document.getElementById('hide-title3').style.visibility = 'visible';
      console.log(err);
    }
  }

  const setAgencyAndUser = async e => {
    e.preventDefault();
    setAgency( e.target.value );
    const userData = await treatApiData.findUserData();
    setUser( userData);
    setUsersIds( [ userData.id ] );
  }

  return (
    <React.Fragment>
      <div className='default-container'>
        <HeaderIn />
        <form className='form' onSubmit={ handler }>
          <div className='form-container'>
            <div className='div-center'>
              <h1 id='hide-title1'>Something wrong has occurred!</h1>
              <h3 id='hide-title3'>Please, check your data and try again.</h3>
            </div>
            <div className='items'>
              <label className='items-label'>Agency</label>
              <InputTextUi placeholder='Agency' typeInput='text' methodBlur={ e => setAgencyAndUser( e ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label className='items-label'>Project code</label>
              <InputTextUi placeholder='Code' typeInput='text' methodChange={ e => setCode( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Title</label>
              <InputTextUi placeholder='Title' typeInput='text' methodChange={ e => setTitle( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Description</label>
              <InputTextUi placeholder='Description' typeInput='text' methodChange={ e => setDescription( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Total funding</label>
              <InputTextUi placeholder='Total ex.: 1295.05' typeInput='number' methodChange={ e => setTotal( parseFloat( e.target.value ) ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Outgoing</label>
              <InputTextUi placeholder='Outgoing ex.: 1295.05' typeInput='number' methodChange={ e => setOutgoing( parseFloat( e.target.value ) ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Start date</label>
              <InputTextUi typeInput='date' methodChange={ e => setStart( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>End date</label>
              <InputTextUi typeInput='date' methodChange={ e => setEnd( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <ButtonUi classCss='items-btn' name='Submit project' methodClick={ e => handler( e ) } />
            </div>
          </div>
        </form>
      </div>
    </React.Fragment>
  )
}

export default FormProject;