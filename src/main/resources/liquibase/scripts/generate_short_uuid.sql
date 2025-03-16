CREATE OR REPLACE FUNCTION generate_short_uuid()
RETURNS VARCHAR(8) AS
'
DECLARE
    full_uuid UUID;
BEGIN

    -- Generate a UUID
    SELECT uuid_generate_v4() INTO full_uuid;

    -- Convert the UUID to a string and take the first 8 characters
    RETURN LEFT(full_uuid::text, 8);
END;
' LANGUAGE plpgsql;