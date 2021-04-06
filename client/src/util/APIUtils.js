import { API_BASE_URL, FILM_LIST_SIZE, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};

export function getAllFilms(page, size) {
    page = page || 0;
    size = size || FILM_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/film",
        method: 'GET'
    });
}

export function createFilm(filmData) {
    return request({
        url: API_BASE_URL + "/film/create",
        method: 'POST',
        body: JSON.stringify(filmData)         
    });
}
export function createComment(commentData,filmSlug) {
    return request({
        url: API_BASE_URL + `/film/${filmSlug}/comment/create`,
        method: 'POST',
        body: JSON.stringify(commentData)
    });
}


export function login(loginRequest) {
    //alert(JSON.stringify(loginRequest));
    return request({
        url: API_BASE_URL + "/api/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/api/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/api/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/api/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}


export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/api/user/me",
        method: 'GET'
    });
}

export function getSingleFilm(username) {
    return request({
        url: API_BASE_URL + "/film/" + username,
        method: 'GET'
    });
}




