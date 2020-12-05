import React from 'react';
import PropTypes from 'prop-types';

import './inputTextUi.css';

const InputTextUi = ( { typeInput, classCss, methodBlur, placeholder, requiredField, methodChange } ) =>
  <React.Fragment>
    <input type={ typeInput } className={`inputText ${ classCss }`} onBlur={ methodBlur } onChange={ methodChange } placeholder={ placeholder } required={ requiredField } />
  </React.Fragment>

InputTextUi.propTypes = {
  typeInput: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
  methodBlur: PropTypes.func,
  methodChange: PropTypes.func,
  classCss: PropTypes.string,
  requiredField: PropTypes.bool
}

export default InputTextUi;