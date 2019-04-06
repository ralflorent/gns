/**
 * Convert snake case string (snake_case) to camel case string (camelCase)
 * @param str snake case string value
 * @return {string} the converted string to camel case
 *
 * @stable
 * @see {link https://vladimir-ivanov.net/camelcase-to-snake_case-and-vice-versa-with-javascript/}
 */
function snakeToCamel(str: string): string {
    return str
        .toLowerCase()
        .replace(/(\_\w)/g, m => m[1].toUpperCase());
}

/**
 * Convert camel case string (camelCase) to snake case string (snake_case)
 * @param str camel case string value
 * @return {string} the converted string to snake case
 *
 * @stable
 */
function camelToSnake(str: string): string {
    return str
        .replace(/[\w]([A-Z])/g, m => `${m[0]}_${m[1]}` )
        .toLowerCase();
}

/**
 * Transform JSON object keys from camel to snake case and viceversa
 * @param source JSON object to walk through
 * @param option camel or snake case
 * @return {object} nested JSON object with the converted keys
 * 
 * @stable
 */
function transform(source: object, option: 'camel'| 'snake' = 'camel'): object {
    const target = {};
    
    for (let key of Object.keys(source)) {
        if (source[ key ] instanceof Object){
            target[ key.toLowerCase() ] = transform( source[ key ], option );
        }
        else {
            target[option === 'camel'? snakeToCamel(key): camelToSnake(key)] = source[key];
        }
    }

    return target;
}

export { transform };