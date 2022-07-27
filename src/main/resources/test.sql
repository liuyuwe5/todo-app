select employee0_.id                 as id1_0_0_,
       employee0_.employee_name      as employee2_0_0_,
       employee0_.employee_unique_id as employee3_0_0_,
       tasks1_.task_name             as task_nam3_1_1_,
       tasks1_.id                    as id1_1_1_,
       tasks1_.id                    as id1_1_2_,
       tasks1_.employee_id           as employee4_1_2_,
       tasks1_.is_completed          as is_compl2_1_2_,
       tasks1_.task_name             as task_nam3_1_2_,
       employee2_.id                 as id1_0_3_,
       employee2_.employee_name      as employee2_0_3_,
       employee2_.employee_unique_id as employee3_0_3_
from employee employee0_
         left outer join task tasks1_ on employee0_.id = tasks1_.task_name
         left outer join employee employee2_ on tasks1_.employee_id = employee2_.id
where employee0_.id = 1

select tasks0_.task_name             as task_nam3_1_0_,
       tasks0_.id                    as id1_1_0_,
       tasks0_.id                    as id1_1_1_,
       tasks0_.employee_id           as employee4_1_1_,
       tasks0_.is_completed          as is_compl2_1_1_,
       tasks0_.task_name             as task_nam3_1_1_,
       employee1_.id                 as id1_0_2_,
       employee1_.employee_name      as employee2_0_2_,
       employee1_.employee_unique_id as employee3_0_2_
from task tasks0_
         left outer join employee employee1_ on tasks0_.employee_id = employee1_.id
where tasks0_.task_name=?[22018 - 214];


select employee0_.id                 as id1_0_,
       employee0_.employee_name      as employee2_0_,
       employee0_.employee_unique_id as employee3_0_
from employee employee0_

select tasks0_.task_name             as task_nam3_1_0_,
       tasks0_.id                    as id1_1_0_,
       tasks0_.id                    as id1_1_1_,
       tasks0_.employee_id           as employee4_1_1_,
       tasks0_.is_completed          as is_compl2_1_1_,
       tasks0_.task_name             as task_nam3_1_1_,
       employee1_.id                 as id1_0_2_,
       employee1_.employee_name      as employee2_0_2_,
       employee1_.employee_unique_id as employee3_0_2_
from task tasks0_
         left outer join employee employee1_ on tasks0_.employee_id = employee1_.id
where tasks0_.task_name=?[22018 - 214]


