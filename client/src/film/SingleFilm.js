import React, {Component, useEffect, useState} from 'react';

import {getCurrentUser, getSingleFilm} from '../util/APIUtils';
import {Avatar, Badge, Button, Icon, notification, Tabs} from 'antd';

import {formatDate} from '../util/Helpers';
import LoadingIndicator from '../common/LoadingIndicator';
import './SingleFilm.css';


import Comment from "./Comment";
import NotFound from "../common/NotFound";
import ServerError from "../common/ServerError.css";
import {Link} from "react-router-dom";
import CommentCard from "./CommentCard"
const TabPane = Tabs.TabPane;

const SingleFilm = (props) => {
    const [film, setFilm] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [notFound, setNotFound] = useState(null);
    const [serverError, setServerError] = useState(null);
    const [filmSlug, setFilmSlug] = useState(null);

    function loadUserProfile(filmslug) {
        setIsLoading(true);

        getSingleFilm(filmslug)
            .then(response => {
                setFilm(response);
                setIsLoading(false);
            }).catch(error => {
            if (error.status === 404) {
                setNotFound(true);
                setIsLoading(false);
            } else {
                setServerError(true);
                setIsLoading(false);
            }
        });
    }


    useEffect(() => {
        const film = props.match.params.filmslug;
        setFilmSlug(film);
        loadUserProfile(film);
    }, []);

    const tabBarStyle = {
        textAlign: 'center'
    };

    if(isLoading) {
        return <LoadingIndicator />;
    }

    if(notFound) {
        return <NotFound />;
    }

    if(serverError) {
        return <ServerError />;
    }

    

    return (

        <div className="profile">
            {
                film ? (
                    <div className="film-content">
                        <div className="film-header">
                            <img src={film.photo} className={"film-banner"}/>
                            <div className="film-creator-info">
                                <Link className="creator-link" to={`/films/${film.slug}`}>
                        <span className="film-creator-username">
                                {film.name}
                        </span>
                                    <span className="film-creation-date">
                                 {formatDate(film.releaseDate)}
                        </span>
                                </Link>
                            </div>
                            <div className="film-description">
                                {film.description}
                            </div>
                        </div>
                        <div className="film-genre">
                            <span className="ant-tag ant-tag-blue">{film.genre}</span>
                        </div>
                        <div className="user-film-details">
                            <Tabs defaultActiveKey="1"
                                  animated={false}
                                  tabBarStyle={tabBarStyle}
                                  size="large"
                                  className="profile-tabs">
                                <TabPane tab={`All Comments`} key="1">
                                    {film.comments.map((comment,index) => {
                                        return <CommentCard comment = {comment} key={index}/>
                                    })}
                                </TabPane>
                                <TabPane tab={`Create Comment `} key="2"  style={{textAlign:"center"}}>
                                    {(props.isAuthenticated) ? <Comment filmSlug = {filmSlug} /> : <div>Please
                                        <Link className="creator-link" to={'/signup'} style={{padding:"0 0.5em"}} >
                                            <Button type="dashed" >Sign Up </Button>
                                        </Link>
                                        Or
                                        <Link className="creator-link" to={'/login'}  style={{padding:"0 0.5em"}} >
                                            <Button type="dashed" > Sign In </Button>
                                        </Link> for posting a comment </div>}

                                </TabPane>
                            </Tabs>
                        </div>
                    </div>
                ) : null
            }
        </div>

    )


}

export default SingleFilm;