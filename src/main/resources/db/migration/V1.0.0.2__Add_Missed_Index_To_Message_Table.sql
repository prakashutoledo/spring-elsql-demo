/**
 * V1.0.0.2__Add_Missed_Index_Message_Table.sql
 * Purpose : Add new index for message_id and user_id to user_message table
 * Author  : Prakash Khadka
 * Date    : 07/25/2021
 * Since   : 1.0.1 
 */
 
/******************************************Script Start******************************************************/

CREATE UNIQUE INDEX user_message_IDX1 ON user_message (message_id, user_id);

/*****************************************Script End*********************************************************/