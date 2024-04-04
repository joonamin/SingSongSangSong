INSERT INTO artist (username, role, age, sex, nickname)
SELECT *
FROM (SELECT "KAKAO_3396195419" AS username, "GUEST" AS role, 27 as age, 'M' as sex, "nickname" as nickname)
         AS tmp
WHERE NOT EXISTS (SELECT username
                  FROM artist
                  WHERE username = "KAKAO_3396195419")
LIMIT 1;

INSERT INTO artist (username, role, age, sex, nickname)
SELECT *
FROM (SELECT "GOOGLE_105489639879872487314" AS username,
             "GUEST"                        AS role,
             27                             as age,
             'M'                            as sex,
             "joon_google"                  as nickname)
         AS tmp
WHERE NOT EXISTS (SELECT username
                  FROM artist
                  WHERE username = "GOOGLE_105489639879872487314")
LIMIT 1;


INSERT INTO file (saved_file_name, original_file_name)
SELECT *
FROM (SELECT "default.jpg" AS fn, "default.jpg" AS ofn) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM file WHERE fn = "default.jpg" AND ofn = "default.jpg")
LIMIT 1;
