export default class Util {
  convertDate = date => {
    return new Date( date ).toLocaleDateString( 'en-US' );
  }

  roundValue = value => {
    const twoPlaces = Math.ceil(value * 100) / 100;
    return twoPlaces.toFixed( 2 );
  }

  printBRL = amount => {
    let returnValue = "";
    const numberString = this.roundValue( amount );
    let part1 = numberString.split( "." )[ 0 ];
    const part2 = numberString.split( "." )[ 1 ];

    if( amount < 0 ) {
      returnValue = "-";
      part1 = part1.substring( 1 );
    }

    if( part1.length > 3 ) {
      part1 = part1.split( /(?=(?:...)*$)/ ).join( '.' );
    }
    
    returnValue = `${ returnValue }R$ ${ part1 },${ part2 }`;

    return returnValue;
  }
}