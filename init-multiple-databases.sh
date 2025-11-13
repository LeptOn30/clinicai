#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    -- Create databases for each microservice as per the architecture
    CREATE DATABASE identity_db;
    CREATE DATABASE patient_db;
    CREATE DATABASE scheduling_db;
    CREATE DATABASE billing_db;
    -- The notification service might not need a DB, but we can add one if needed
    -- CREATE DATABASE notification_db;

    -- Grant all privileges on these new databases to the admin user
    GRANT ALL PRIVILEGES ON DATABASE identity_db TO admin;
    GRANT ALL PRIVILEGES ON DATABASE patient_db TO admin;
    GRANT ALL PRIVILEGES ON DATABASE scheduling_db TO admin;
    GRANT ALL PRIVILEGES ON DATABASE billing_db TO admin;
EOSQL