/**
 * GNS Global constanst across the entire application
 *
 * Created on March 23, 2019
 * @author Ralph Florent <ralflornt@gmail.com>
 */
import { environment as ENV } from 'src/environments/environment';

// ref: https://openmaptiles.com/downloads/tileset/osm/europe/germany/bremen/?usage=personal
export const GNS_CONSTANTS = {
    api: {
        BASE_URL: `${ENV.apiServiceUrl}/api/v1/notes`
    },
    leaflet: {
        ATTRIBUTION: `Map data &copy; <a href="https://www.openstreetmap.org/">
            OpenStreetMap</a> contributors,
            <a href="https://creativecommons.org/licenses/by-sa/2.0/">
            CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>`,
        TOKEN: 'pk.eyJ1IjoicmFsZmxvcmVudCIsImEiOiJjanRjOWZnc2EwczkxNGFwYzFxaWVyeGJ2In0.sf6zess8p_6WcYWSvI2ADg',
        ONLINE_URL: `https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}`,
        STATIC_URL: `assets/tiles/bremen-tile.mbtiles`,
        API_URL: `${ENV.apiServiceUrl}/api/v1/notes/bremen-tile`
    }
}

export const VALIDATION_RULES: GNSValidationRule = {
    notebook: {
        description: {
            rules: {
                REQUIRED: true,
                MIN_LENGTH: 3,
                MAX_LENGTH: 250
            },
            messages: {
                required: 'Please enter a description',
                minlength: 'Must be at least 3 characters',
                maxlength: 'Must be less than 250 characters'
            }
        },
        note: {
            rules: { REQUIRED: true },
            messages: { required: 'Please enter a note'}
        },
        createdBy: {
            rules: {
                REQUIRED: true,
                MIN_LENGTH: 2,
                MAX_LENGTH: 50,
                PATTERN: '^[a-zA-Z0-9]+(([a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$'
            },
            messages: {
                required: 'Please enter your name',
                minlength: 'Must be at least 2 characters',
                maxlength: 'Must be less than 50 characters',
                pattern: 'Should be alphanumerical characters only'
            }
        }
    }
}

export interface GNSValidationRule {
    notebook: {
        description: Partial<GNSRuler>;
        note: Partial<GNSRuler>;
        createdBy: Partial<GNSRuler>;
    }
}

export interface GNSRuler {
    rules: {
        [key: string]: boolean | number | string;
    };
    messages: {
        [key: string]: string | string[];
    };
}