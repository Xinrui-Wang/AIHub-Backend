package com.example.backend.config;

/**
 * Manages database table names as constants for easy reference throughout the codebase.
 * This approach centralizes table name management, reducing the risk of errors caused by hardcoding.
 */
public class TableNames {

    /**
     * Table name for user account information.
     * Replace with the actual name used in your database schema.
     */
    public static final String USER = "user_accounts";

    /**
     * Table name for storing all user chat sessions.
     * Replace with the actual name used in your database schema.
     */
    public static final String SESSION = "all_user_chat_sessions";
}
