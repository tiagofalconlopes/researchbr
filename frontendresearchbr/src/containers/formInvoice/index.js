import React, { useState } from 'react';
import { ButtonUi, HeaderIn, InputTextUi } from '../../components';
import ResearchApi from '../../model/researchApi';

import TreatApiData from '../../service/treatApiData';

function FormInvoice( props ) {
  const [ code, setCode ] = useState( '' );
  const [ value, setValue ] = useState( 0.00 )
  const [ date, setDate ] = useState();
  const [ project, setProject ] = useState();

  const reasearchApi = new ResearchApi();
  const treatApiData = new TreatApiData();

  const handler = async e => {
    e.preventDefault();
    try {
      if( project && code && value && date ) {
        document.getElementById('hide-title1').style.visibility = 'hidden';
        document.getElementById('hide-title3').style.visibility = 'hidden';
        await reasearchApi.createANewInvoice( { project, code, value, date } );
        treatApiData.verifyToken();
      } 
    } catch (err) {
      document.getElementById('hide-title1').style.visibility = 'visible';
      document.getElementById('hide-title3').style.visibility = 'visible';
      console.log(err);
    }
  }

  const setCodeAndProject = async e => {
    e.preventDefault();
    let pageURL = window.location.href;
    let lastURLSegment = pageURL.substr( pageURL.lastIndexOf('/') + 1 );
    setCode( e.target.value );
    const prjData = await reasearchApi.findProjectById( lastURLSegment );
    setProject( { id: prjData.id });
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
              <label className='items-label'>Invoice code</label>
              <InputTextUi placeholder='Code' typeInput='text' methodBlur={ e => setCodeAndProject( e ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Total value</label>
              <InputTextUi placeholder='Total ex.: 1295.05' typeInput='number' methodChange={ e => setValue( parseFloat( e.target.value ) ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Date</label>
              <InputTextUi typeInput='date' methodChange={ e => setDate( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <ButtonUi classCss='items-btn' name='Submit invoice' methodClick={ e => handler( e ) } />
            </div>
          </div>
        </form>
      </div>
    </React.Fragment>
  )
}

export default FormInvoice;