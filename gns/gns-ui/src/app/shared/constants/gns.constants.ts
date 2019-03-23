import { Notebook } from '../models/notebook.model';

export const GNS_CONSTANTS = {
    api: {
        BASE_URL: `http://10.10.10.11:8080/api/v1/notes`
    },
    leaflet: {
        ATTRIBUTION: `Map data &copy; <a href="https://www.openstreetmap.org/">
            OpenStreetMap</a> contributors, 
            <a href="https://creativecommons.org/licenses/by-sa/2.0/">
            CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>`,
        TOKEN: 'pk.eyJ1IjoicmFsZmxvcmVudCIsImEiOiJjanRjOWZnc2EwczkxNGFwYzFxaWVyeGJ2In0.sf6zess8p_6WcYWSvI2ADg',
        ONLINE_URL: `https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}`,
        STATIC_URL: `assets/tiles/bremen-tile.mbtitles`,
        API_URL: `http://10.10.10.11:8080/api/v1/notes/bremen-tile`
    }
}

export const VALIDATION_RULES = {
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
        }
    }
}