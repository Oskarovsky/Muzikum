'use strict';

export const API_BASE_URL = 'https://localhost:8443';
export const ACCESS_TOKEN = 'oskarroSecretKey';

export const OAUTH2_REDIRECT_URI = 'https://localhost:4200/oauth2/redirect';

export const GOOGLE_AUTH_URL = API_BASE_URL + '/api/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL = API_BASE_URL + '/api/oauth2/authorize/facebook?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL = API_BASE_URL + '/api/oauth2/authorize/github?redirect_uri=' + OAUTH2_REDIRECT_URI;
