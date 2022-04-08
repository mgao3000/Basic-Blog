insert into users (username, password_hash, fullname) values
('admin', 'admin', 'administrator'),
('David', 'David', 'David Joe'),
('Jordon', 'Jordon', 'Jordon Ford'),
('Mike', 'Mike', 'Mike Ashley'),
('Clinton', 'Clinton', 'Clinton Gray'),
('John', 'John', 'John Anderson'),
('Frank', 'Frank', 'Frank Adams')
;

insert into posts (title, body, post_date, user_id) values
('Great News', 'News Content', CURRENT_DATE, 2),
('Admin Notification', 'Notification Content', CURRENT_DATE, 1),
('Usage Rule', 'Usage Rule Content', CURRENT_DATE, 1),
('Share Happiness', 'Happy Content', CURRENT_DATE, 3),
('New Game', 'New Game Content', CURRENT_DATE, 4),
('Game Update', 'Game Update Content', CURRENT_DATE, 4),
('Weather', 'Weather Content', CURRENT_DATE, 5),
('History', 'History Content', CURRENT_DATE, 6),
('World War II', 'World War II Content', CURRENT_DATE, 7),
('Dog Story', 'Dog Story Content', CURRENT_DATE, 5),
('Garden Plan', 'Garden Plan Content', CURRENT_DATE, 6),
('School News', 'School News Content', CURRENT_DATE, 7),
('Car News', 'Car News Content', CURRENT_DATE, 7);

commit;