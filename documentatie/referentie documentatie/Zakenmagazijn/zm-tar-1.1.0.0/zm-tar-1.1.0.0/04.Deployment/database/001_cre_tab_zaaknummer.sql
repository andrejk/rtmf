/* Changelog:
 * 
 * 2009-08-06 Erwin Bolwidt <ebolwidt@ioo.rotterdam.nl>, <ebolwidt@worldturner.nl>
 * [] primary key constraint added; the java code fails if a duplicate row would occur but there was nothing in the DDL to prevent that
 */

create table volgnummers (
   zaaktypecode         varchar2(50)                    not null,
   jaar                 number(4,0)                     not null,
   maand                number(2,0)                     not null,
   volgnummer           number(19)                      not null,
   constraint volgnummers_pk primary key (zaaktypecode, jaar, maand)
);