create table todoapp.task (
                              id bigint not null auto_increment,
                              is_completed bit not null,
                              task_name varchar(255) not null,
                              employee_id bigint,
                              primary key (id)
);
