accept nm_user char prompt 'Naam database user      : '
accept ww_user char prompt 'Wachtwoord databae user : '

create user &nm_user identified by &ww_user;
grant connect, resource to &nm_user;
