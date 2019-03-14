/**
 * Helper function to convert snake case string (snake_case) to camel case 
 * string (camelCase)
 * @param str string value to convert
 * @return {string} the converted string to camel case
 * 
 * @stable
 */
function snakeToCamel(str: string): string {
    return str
        .toLowerCase()
        .replace(/(\_\w)/g, m => m[1].toUpperCase());
}

/**
 * Recursive function to transform constants following the NAMING_CONVENTION to 
 * interface-like castable object
 * @param source JSON object to walk through 
 * @return {object} nested JSON object to be cast
 * 
 * @stable
 */
function transform(source: object): object {
    const target = {};
    
    for (let key of Object.keys(source)) {
        if (source[ key ] instanceof Object){
            target[ key.toLowerCase() ] = transform( source[ key ] );
        }
        else {
            target[ snakeToCamel( key.toLowerCase() ) ] = source[ key ];
        }
    }

    return target;
}

export { transform };