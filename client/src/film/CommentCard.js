import React from 'react';
import ReactDOM from 'react-dom';
import { Card } from 'antd';



const CommentCard = ({comment}) => {


    return (

        <Card style={{ width: '100%' }}>
              <p>{comment}</p>
        </Card>

    )
};

export default CommentCard;