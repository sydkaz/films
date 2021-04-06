import React, {Component} from 'react';
import './Film.css';
import {Avatar, Icon} from 'antd';
import {Link} from 'react-router-dom';

import {formatDate} from '../util/Helpers';
import {Radio, Button, Tag, Badge} from 'antd';

const Film = (props) => {
    return (
        <div className="film-content">
            <div className="film-header">
                <img src={props.film.photo} className={"film-banner"}/>
                <div className="film-creator-info">
                    <Link className="creator-link" to={`/films/${props.film.slug}`}>
                        <span className="film-creator-username">
                                {props.film.name}
                        </span>
                        <span className="film-creation-date">
                                {formatDate(props.film.releaseDate)}
                        </span>
                    </Link>
                </div>
                <div className="film-description">
                    {props.film.description}
                </div>
            </div>
            <div className="film-genre">
                <span className="ant-tag ant-tag-blue">{props.film.genre}</span>
            </div>
            <div className="film-footer">
                <Link className="creator-link" to={`/films/${props.film.slug}`}  >
                    <Button type="dashed" >
                        <Icon type="plus" /> Read Comments
                        <Badge count={props.film.comments.length} offset={[-20, 20]}>
                            <span className="head-example" />
                        </Badge>
                    </Button>
                </Link>
            </div>
        </div>
    );

}


export default Film;