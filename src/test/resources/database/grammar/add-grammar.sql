INSERT INTO grammars (
    id, title, image_url, description, video_url,
    main_sub_title, second_title, third_title, third_sub_title,
    fourth_title, fourth_sub_title, under_title_list, href, is_deleted, type_id
)
VALUES (
           1,
           'Grammar title',
           'Grammar image url',
           'Grammar description',
           'Grammar video url',
           'Grammar main sub title',
           'Grammar second title',
           'Grammar third title',
           'Grammar third sub title',
           'Grammar fourth title',
           'Grammar fourth sub title',
           '["First under title", "Second under title"]',
           'Grammar href',
            0,
            1
       );
