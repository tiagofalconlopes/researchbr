import React from 'react';
import { Link } from 'react-router-dom';

import './headerIn.css';

const HeaderIn = () => {
  const logout = e => {
    e.preventDefault();
    window.localStorage.clear();
    window.location.replace( 'http://localhost:3000/login' );
  }

  return (
    <nav>
      <div className='nav-div'>
        <Link className='header-nav-link' to='/'><button className='nav-button'>Home</button></Link>
        <button  className='nav-button' onClick={ e => logout( e ) }>logout</button>
      </div>
    </nav>
  )
}

export default HeaderIn;