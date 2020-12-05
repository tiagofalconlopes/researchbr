import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';

import './buttonUi.css';

const ButtonUi = ( { classCss, methodClick, name, link } ) =>
  <React.Fragment>
    <button className={`btn ${ classCss }`} onClick={ methodClick }>
      { link ? <Link to={ link } className='btn-link'>{ name }</Link> : name }
    </button>
  </React.Fragment>

ButtonUi.propTypes = {
  name: PropTypes.string.isRequired,
  methodClick: PropTypes.func,
  classCss: PropTypes.string,
  link: PropTypes.string
}

export default ButtonUi;