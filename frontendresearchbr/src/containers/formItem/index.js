import React, { useState } from 'react';
import { ButtonUi, HeaderIn, InputTextUi } from '../../components';
import ResearchApi from '../../model/researchApi';

import TreatApiData from '../../service/treatApiData';

function FormItem( props ) {
  const [ name, setName ] = useState( '' );
  const [ value, setValue ] = useState( 0.00 )
  const [ quantity, setQuantity ] = useState();
  const [ description, setDescription ] = useState();
  const [ category, setCategory ] = useState( '' );
  const [ categories, setCategories ] = useState( [] );
  const [ invoice, setInvoice ] = useState();
  const [ invoiceList, setInvoiceList ] = useState( [] );

  const researchApi = new ResearchApi();
  const treatApiData = new TreatApiData();

  const handler = async e => {
    e.preventDefault();
    try {
      if( name && value && quantity && description && category && invoice ) {
        document.getElementById('hide-title1').style.visibility = 'hidden';
        document.getElementById('hide-title3').style.visibility = 'hidden';
        await researchApi.createANewItem( { name, value, quantity, description, category, invoice } );
        treatApiData.verifyToken();
      } 
    } catch (err) {
      document.getElementById('hide-title1').style.visibility = 'visible';
      document.getElementById('hide-title3').style.visibility = 'visible';
      console.log(err);
    }
  }

  const setNameAndRequests = async e => {
    e.preventDefault();
    setName( e.target.value );
    await setRequests( e );
  }

  const setRequests = async e => {
    e.preventDefault();
    let pageURL = window.location.href;
    let lastURLSegment = pageURL.substr( pageURL.lastIndexOf('/') + 1 );
    await researchApi.findProjectById( lastURLSegment ).then( res => {
      if( res ) {
        let ids = res.invoices;
        let aux = [];
        ids.forEach(element => {
          if(typeof element === 'number' ) {
            aux.push( researchApi.findInvoiceById( element ) )
          }
          if(typeof element === 'object' ) {
            aux.push( researchApi.findInvoiceById( element.id ) )
          }
        });
        Promise.all( aux )
          .then( res => {
            setInvoiceList( res )
          })
      }
    } );
    await researchApi.findAllCategories().then( res => setCategories( res ) );
  }

  const setCategoryName = catName => {
    setCategory( { name: catName } )
  }

  const setInvoiceId = invId => {
    setInvoice( { id: parseInt( invId ) } );
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
              <label className='items-label'>Item name</label>
              <InputTextUi placeholder='Name' typeInput='text' methodBlur={ e => setNameAndRequests( e ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Total value</label>
              <InputTextUi placeholder='Value ex.: 1295.05' typeInput='number' methodChange={ e => setValue( parseFloat( e.target.value ) ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Quantity</label>
              <InputTextUi placeholder='Quantity ex.: 3' typeInput='number' methodChange={ e => setQuantity( parseInt( e.target.value ) ) } requiredField={ true } />
            </div>
            <div className='items'>
              <label  className='items-label'>Description</label>
              <InputTextUi placeholder='Description' typeInput='text' methodChange={ e => setDescription( e.target.value ) } requiredField={ true } />
            </div>
            <div className='items'>
              <select className='items-select' onChange={ e => e.target.value !== '-1' ? setCategoryName( e.target.value ) : null } required>
                <option value="-1">select a category</option>
                {
                  categories && (
                    categories.map( cat => {
                      return <option key={ cat.name } value={ cat.name }>{ cat.name }</option>
                    } )
                  )
                }
              </select>
            </div>
            <div className='items'>
              <select className='items-select' onChange={ e => e.target.value !== '-1' ? setInvoiceId( parseInt( e.target.value ) ) : null } required>
                <option value="-1">select an invoice</option>
                {
                  invoiceList && (
                    invoiceList.map( inv => {
                      
                      return <option key={ inv.id } value={ inv.id }>{ inv.code }</option>
                    } )
                  )
                }
              </select>
            </div>
            <div className='items'>
              <ButtonUi classCss='items-btn' name='Submit item' methodClick={ e => handler( e ) } />
            </div>
          </div>
        </form>
      </div>
    </React.Fragment>
  )
}

export default FormItem;