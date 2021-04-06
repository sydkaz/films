import React, {Component, useState, useEffect} from 'react';
import {getAllFilms, getUserCreatedFilms, getUserVotedFilms} from '../util/APIUtils';
import Film from './Film';
import {castVote} from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';
import {Button, Icon, notification} from 'antd';
import {FILM_LIST_SIZE} from '../constants';
import {withRouter} from 'react-router-dom';
import './FilmList.css';

const FilmList = (props) => {
    const [films, setFilms] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    function loadFilmList(page = 0, size = FILM_LIST_SIZE) {
        let promise = getAllFilms(page, size);
        if (!promise) {
            return;
        }
        setIsLoading(true)
        promise
            .then(response => {
                setFilms(response)
                setIsLoading(false)
            }).catch(error => {
            setIsLoading(false)
        });
    }

    useEffect(() => {
        loadFilmList();
    }, []);


    const filmViews = [];
    films.forEach((film, filmIndex) => {
        filmViews.push(<Film key={film.id} film={film}/>)
    });

    return (
        <div className="films-container">
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