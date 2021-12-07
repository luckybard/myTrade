INSERT INTO `user` (`id`,`email`,`highlight_points`,`password`,`role`,`username`) VALUES (1,'brad@brad.brad',5,'$2a$10$XiR5jGex8RjaxN5vRVsdCuHtt3579.XdJGpND.yt.YzVYkNB2Bsli','USER','brad@brad.brad');
INSERT INTO `user` (`id`,`email`,`highlight_points`,`password`,`role`,`username`) VALUES (2,'kate@kate.kate',5,'$2a$10$/QObTKF0qKaDU047f3DLWOYfchCeq.x6K9iMKGZe///3/CuXKJGS.','USER','kate@kate.kate');
INSERT INTO `user` (`id`,`email`,`highlight_points`,`password`,`role`,`username`) VALUES (3,'john@john.john',5,'$2a$10$lS1sZCcBjTQXT672G2/HT.r3DgvE01E4mOLvbdMWQ53c541VLOCiq','USER','john@john.john');
INSERT INTO `user` (`id`,`email`,`highlight_points`,`password`,`role`,`username`) VALUES (4,'adam@adam.adam',5,'$2a$10$UhdE7Wj1bfwi5SvXrzdK2uRms7NfdQlMkcC2h3BSehLWnMDSkmgnm','USER','adam@adam.adam');
INSERT INTO `user` (`id`,`email`,`highlight_points`,`password`,`role`,`username`) VALUES (5,'mike@mike.mike',5,'$2a$10$SPdo6xnJWx7GrjKLkLXAGerIclvMnfjOy3rx3.VslhXhzsfq9nud.','USER','mike@mike.mike');
#
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (1,'FURNITURE','LONDON',5,'2021-11-27','As titled. In great condition, like new. Very easy to assemble. Mirrored door, inside has 3 shelves, big one on top and two smaller ones down the side and a clothes rail of course.','2020-11-27',true,'2021-11-27','brad@brad.brad',75,'2021-11-27','Corner wardrobe mirror door');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (2,'FURNITURE','LONDON',10,'2021-11-27','This set consists of a second-hand walnut wood dining table and 8 brown leather chairs in excellent condition. The dining table is adjustable and can seat 8 or the middle panel can slide in and become smaller and seat 6. The set has an antique look, stately appeal, and a graceful appearance. There are no major damages on the table or any of the chairs outside of normal wear and tear.','2020-11-27',true,'2021-11-27','brad@brad.brad',600,'2021-11-27','Walnut wood table and 8 leather chairs');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (3,'FURNITURE','LONDON',15,'2021-11-27','2 side tables in metal. They have a cobber like finish which patinas beautifully. Height: 56 cm, diameter: 39 cm. 3 legs £30 each','2020-11-27',true,'2021-11-27','brad@brad.brad',60,'2021-11-27','Side tables');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (4,'CLOTHES','PARIS',200,'2021-11-27','Disney Princess Cinderella Pyjamas Pajamas Pjs Girls Toddlers 18 months to 5 Years Official  Cinderella Pyjama Set (Secret Princess) DESIGN: Each pair is a two piece set with a long sleeved top and full length trousers Cotton Blend Machine Washable, Fastening: Pull On','2020-11-27',false,'2021-11-27','kate@kate.kate',10,'2021-11-27','Disney Princess Cinderella Pyjamas Pajamas Girls Toddlers');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (5,'CLOTHES','PARIS',250,'2021-11-27',' Formed from an opulent mix of wool and cashmere, the Kikiie coat will cocoon you in a layer of luxury. It boasts a high standing neckline and a waist-cinching belt to simultaneously banish the elements and accentuate your figure.','2020-11-27',false,'2021-11-27','kate@kate.kate',250,'2021-11-27','Ted Baker Kikiie Wrap Coat Pale Nude Pink Wool Cashmere');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (6,'BOOKS','WARSAW',300,'2021-11-27','The Fellowship of the Ring is the first of three volumes of the epic novel The Lord of the Rings by the English author J. R. R. Tolkien. It is followed by The Two Towers and The Return of the King. It takes place in the fictional universe of Middle-earth. It was originally published on 29 July 1954 in the United Kingdom.','2020-11-27',true,'2021-11-27','john@john.john',15,'2021-11-27','The Lord of the Rings');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (7,'BOOKS','WARSAW',310,'2021-11-27','The Two Towers is the second volume of J. R. R. Tolkien\'s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.','2020-11-27',true,'2021-11-27','john@john.john',15,'2021-11-27','The Lord of the Rings');
INSERT INTO `ad` (`id`,`ad_category`,`city`,`count_view`,`created_date`,`description`,`expiration_highlight_date`,`is_active`,`modified_date`,`owner_username`,`price`,`refresh_date`,`title`) VALUES (8,'BOOKS','WARSAW',320,'2021-11-27','The Return of the King is the third and final volume of J. R. R. Tolkien\'s The Lord of the Rings, following The Fellowship of the Ring and The Two Towers. It was published in 1955. The story begins in the kingdom of Gondor, which is soon to be attacked by the Dark Lord Sauron.','2020-11-27',true,'2021-11-27','john@john.john',15,'2021-11-27','The Lord of the Rings');
#
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (1,1);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (1,2);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (1,3);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (2,4);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (2,5);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (3,6);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (3,7);
INSERT INTO `user_ad_entity_list` (`user_entity_id`,`ad_entity_list_id`) VALUES (3,8);
#
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (4,6);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (3,2);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (3,3);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (1,6);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (1,7);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (1,8);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,1);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,2);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,3);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,4);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,5);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,6);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,7);
INSERT INTO `user_favourite_ad_entity_list` (`user_entity_id`,`favourite_ad_entity_list_id`) VALUES (2,8);
#
INSERT INTO `conversation` (`id`,`recipient_username`,`sender_username`,`title`) VALUES (1,'john@john.john','adam@adam.adam','The Lord of the Rings');
INSERT INTO `conversation` (`id`,`recipient_username`,`sender_username`,`title`) VALUES (2,'brad@brad.brad','mike@mike.mike','Walnut wood table and 8 leather chairs');
#
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (1,'adam@adam.adam','2021-11-26 22:26:16.342276','Hi, I\'m interested in buying your book.');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (2,'john@john.john','2021-11-26 22:27:30.493552','Hello! ');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (3,'john@john.john','2021-11-26 22:28:55.963409','Call me to arrange the meeting. Here is my number 0123456789.');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (4,'mike@mike.mike','2021-11-26 22:28:55.963409','Hi');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (5,'mike@mike.mike','2021-11-26 22:29:55.963409','I''d like to buy your table');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (6,'mike@mike.mike','2021-11-26 22:30:55.963409','without chairs');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (7,'mike@mike.mike','2021-11-26 22:31:55.963409','is it alright');
INSERT INTO `message` (`id`,`author_username`,`date_time`,`text`) VALUES (8,'brad@brad.brad','2021-11-26 23:28:55.963409','yes, sure !');
#
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (1,1);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (1,2);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (1,3);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (2,4);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (2,5);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (2,6);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (2,7);
INSERT INTO `conversation_message_list` (`conversation_entity_id`,`message_list_id`) VALUES (2,8);
