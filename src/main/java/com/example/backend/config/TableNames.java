package com.example.backend.config;

/**
 * This class manages all database table names as constants.
 * By centralizing table name management here, we reduce the risk of errors due to hardcoded strings.
 * This helps maintain consistent table names across the codebase and makes it easier to manage changes in the future.
 */
public class TableNames {

    /**
     * The table name for user account information.
     * Represents the table storing user credentials and basic information.
     */
    public static final String USER = "user_accounts";

    /**
     * The table name for storing all user chat sessions.
     * This table contains records of each chat session between users and the system.
     */
    public static final String SESSION = "all_user_chat_sessions";

    /**
     * The table name for storing messages in each chat session.
     * Contains the actual chat message content, sender, type, and timestamp for each session.
     */
    public static final String SESSION_MESSAGES = "all_user_chat_session_messages";
}
