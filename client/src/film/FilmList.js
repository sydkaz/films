import React, {Component, useState, useEffect} from 'react';
import {getAllFilms, getUserCreatedFilms, getUserVotedFilms} from '../util/APIUtils';
import Film from './Film';
import LoadingIndicator from '../common/LoadingIndicator';
import {Button, Icon, notification} from 'antd';
import {API_BASE_URL, FILM_LIST_SIZE} from '../constants';
import {withRouter} from 'react-router-dom';
import './FilmList.css';
import SockJsClient from 'react-stomp'

const FilmList = (props) => {
    let stompClient = null;
    const [films, setFilms] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [connected, setConnected] = useState(false);



    const filmViews = [];
    films.forEach((film, filmIndex) => {
        filmViews.push(<Film key={film.id} film={film}/>)
    });


    return (

        <div className="films-container">
            <SockJsClient url={`${API_BASE_URL}/ws`}
                          topics={['/topic/films']}
                          ref={ (client) => { this.clientRef = client }}
                          onMessage={(films) => {
                              setIsLoading(false);
                              setFilms(films);
                          }}
                          onConnect={()=>{
                              this.clientRef.sendMessage(`/allfilms`)
                          }}
            />
            {filmViews}
            {
                !isLoading && films.length === 0 ? (
                    <div className="no-films-found">
                        <span>No Movies Found.</span>
                    </div>
                ) : null
            }

            {
                isLoading ?
                    <LoadingIndicator/> : null
            }
        </div>
    );
}

export default withRouter(FilmList);