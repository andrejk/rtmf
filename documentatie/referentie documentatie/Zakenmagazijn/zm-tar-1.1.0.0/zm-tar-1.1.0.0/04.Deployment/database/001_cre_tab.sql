/*==============================================================*/
/* DBMS name:      ORACLE Version 10                            */
/* Created on:     26-3-2008                                    */
/*==============================================================*/

/* Changelog:
 * 
 * 2009-08-06 Erwin Bolwidt <ebolwidt@ioo.rotterdam.nl>, <ebolwidt@worldturner.nl>
 * [] All columns that map to a java.lang.Long or a primitive Java long (64 bits) now have oracle type 'number(19)' instead of 'number'. 
 *    'number' is a shorthand for 'number(38)' which cannot be held in a java long.
 * [] zaken.zaakidentificatie: not null constraint added
 * [] unique constraints added on zaken (zaakidentificatie, oge_id) and verzoeken (verzoekidentificate, oge_id)
 * [] medewerkers.medewerkercode: not null constraint added
 * [] unique constraint added on medewerkers (medewerkercode, oge_id)
 * 
 * 2009-07-22 Thomas Delnoij
 * [] All sequences start at value 100. This reserves ids under 100 for test data.
 */
create sequence act_seq start with 100;

create sequence bpt_seq start with 100;

create sequence dmt_seq start with 100;

create sequence gzk_seq start with 100;

create sequence mdw_seq start with 100;

create sequence oeh_seq start with 100;

create sequence seq_tdm start with 100;

create sequence sta_seq start with 100;

create sequence stp_seq start with 100;

create sequence zak_seq start with 100;

create sequence zdt_seq start with 100;

create sequence zfr_seq start with 100;

create sequence zte_seq start with 100;

create sequence zkk_seq start with 100;

create sequence zhk_seq start with 100;

create sequence ztd_seq start with 100;

create sequence lce_seq start with 100;

create sequence zle_seq start with 100;

create sequence vrz_seq start with 100;

create sequence vzk_seq start with 100;

create sequence vfr_seq start with 100;

/*==============================================================*/
/* Table: actoren                                               */
/*==============================================================*/
create table actoren  (
   id                   number(19)                      not null,
   mdw_id               number(19), -- unique??
   oeh_id               number(19), -- unique??
   oge_id               number(19)                      not null,
   functie              varchar2(50)                    not null,
   datumuitdienst       date,
   emailadres1          varchar2(80),
   emailadres2          varchar2(80),
   emailadres3          varchar2(80),
   telefoonnummer1      varchar2(20),
   telefoonnummer2      varchar2(20),
   telefoonnummer3      varchar2(20),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   constraint act_pk primary key (id) using index,
   constraint act_uk unique (mdw_id, oeh_id, oge_id) using index
);

comment on table actoren is
'ACT';

/*==============================================================*/
/* Index: act_mdw_fk_i                                          */
/*==============================================================*/
create index act_mdw_fk_i on actoren (
   mdw_id asc
)
;

/*==============================================================*/
/* Index: act_oeh_fk_i                                          */
/*==============================================================*/
create index act_oeh_fk_i on actoren (
   oeh_id asc
)
;

/*==============================================================*/
/* Table: beperkte_actoren                                      */
/*==============================================================*/
create table beperkte_actoren  (
   id                   number(19)                      not null,
   oge_id               number(19)                      not null,
   naam_mdw             varchar2(120),
   roepnaam_mdw         varchar2(30),
   voorletters_mdw      varchar2(10),
   achternaam_mdw       varchar2(80),
   voorvoegselachternaam_mdw varchar2(10),
   code_mdw             varchar2(20),
   omschrijving_oeh     varchar2(80),
   code_oeh             varchar2(50),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   constraint bpt_pk primary key (id) using index     
);

comment on table beperkte_actoren is
'BPT';

/*==============================================================*/
/* Index: bpt_i1                                                */
/*==============================================================*/
create index bpt_i1 on beperkte_actoren (
   naam_mdw asc
)
;

/*==============================================================*/
/* Table: documenten                                            */
/*==============================================================*/
create table documenten  (
   id                   number(19)                      not null,
   mimetype             varchar2(100)                   default 'application/octet-stream',
   oge_id               number(19)                      not null,
   bronapplicatie       varchar2(100)                   not null,
   externe_dms_id       varchar2(100)                   not null,
   webplus_zaakcode     varchar2(25),
   bestandsnaam         varchar2(256)                   not null,
   bestandsextensie     varchar2(25),
   bestandsgrootte      number(19),
   datumaanmaak         date,
   omschrijving         varchar2(100),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   documentidentificatie varchar2(50),
   datumverzending      date,
   ingangsdatum         date,
   vervaldatum          date,
   datumpublicatie      date,
   constraint dmt_pk primary key (id)
         using index
       ,
   constraint dmt_uk unique (bronapplicatie, externe_dms_id, oge_id)
         using index      
)
;

comment on table documenten is
'DMT';

/*==============================================================*/
/* Table: gerelateerdezaken                                     */
/*==============================================================*/
create table gerelateerdezaken  (
   id                   number(19)                      not null,
   zak_hoofd_id         number(19)                      not null,
   zak_sub_id           number(19)                      not null,
   oge_id               number(19)                      not null,
   aanmaakdatum         date                            default sysdate not null,
   aangemaakt_door      varchar2(100),
   constraint gzk_pk primary key (id)
         using index
       ,
   constraint gzk_uk unique (zak_hoofd_id, zak_sub_id)
         using index
)
;

comment on table gerelateerdezaken is
'GZK';

/*==============================================================*/
/* Index: gzk_zak_fk_1_i                                        */
/*==============================================================*/
create index gzk_zak_fk_1_i on gerelateerdezaken (
   zak_hoofd_id asc
)
;

/*==============================================================*/
/* Index: gzk_zak_fk_2_i                                        */
/*==============================================================*/
create index gzk_zak_fk_2_i on gerelateerdezaken (
   zak_sub_id asc
)
;

/*==============================================================*/
/* Table: medewerkers                                           */
/*==============================================================*/
create table medewerkers  (
   id                   number(19)                      not null,
   achternaam           varchar2(80)                    not null,
   roepnaam             varchar2(30)                    not null,
   geslachtsaanduiding  varchar2(1)                     default 'O' not null
      constraint mdw_geslachtsaanduiding_ck check (geslachtsaanduiding in ('O','M','V')) not deferrable,
   oge_id               number(19)                      not null,
   voorletters          varchar2(10),
   voorvoegselachternaam varchar2(10),
   medewerkercode       varchar2(20)                    not null,
   medewerkertoelichting varchar2(1000),
   aanmaakdatum         date                            default sysdate not null,
   aangemaakt_door      varchar2(100),
   constraint mdw_pk primary key (id)
         using index,
   constraint mdw_uk unique (medewerkercode, oge_id)
         using index
)
;

comment on table medewerkers is
'MDW';

/*==============================================================*/
/* Table: metadata_zaaktypen                                    */
/*==============================================================*/
create table metadata_zaaktypen  (
   naam                 varchar2(30)                    not null,
   oge_id               number(19)                      not null,
   itemtype             varchar2(1)                     not null,
   verplicht            varchar2(1)                    default 'N' not null,
   verwerkt             varchar2(1)                    default 'N' not null,
   lengte               number(19),
   toelichting          varchar2(4000),
   waarde_validatie     varchar2(4000),
   select_query         varchar2(4000),
   label                varchar2(100),
   datatype             varchar2(30)                    not null,
   decimaal             number(19),
   javascripts          varchar2(4000),
   disabled             varchar2(1)                    default 'N',
   default_waarde       varchar2(4000),
   volgorde             number(19),
   constraint mzt_uk unique (naam, oge_id)
         using index 
);

/*==============================================================*/
/* Table: organisatorischeeenheden                              */
/*==============================================================*/
create table organisatorischeeenheden  (
   id                   number(19)                      not null,
   contactpersoon       varchar2(40),
   contactpersoon_mdw_id number(19),
   verantwoordelijke    varchar2(40),
   verantwoordelijke_mdw_id number(19),
   oge_id               number(19)                      not null,
   datumontstaan        date                            not null,
   organisatiecode      varchar2(50)                    not null,
   naam                 varchar2(50)                    not null,
   naamverkort          varchar2(25),
   datumopheffing       date,
   emailadres1          varchar2(80),
   emailadres2          varchar2(80),
   emailadres3          varchar2(80),
   faxnummer1           varchar2(20),
   faxnummer2           varchar2(20),
   faxnummer3           varchar2(20),
   omschrijving         varchar2(80),
   telefoonnummer1      varchar2(20),
   telefoonnummer2      varchar2(20),
   telefoonnummer3      varchar2(20),
   toelichting          varchar2(1000),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   constraint oeh_pk primary key (id)
         using index
       ,
   constraint oeh_uk unique (organisatiecode, oge_id)
         using index
);

comment on table organisatorischeeenheden is
'OEH';

/*==============================================================*/
/* Index: oeh_mdw_fk_1_i                                        */
/*==============================================================*/
create index oeh_mdw_fk_1_i on organisatorischeeenheden (
   contactpersoon_mdw_id asc
);

/*==============================================================*/
/* Index: oeh_mdw_fk_2_i                                        */
/*==============================================================*/
create index oeh_mdw_fk_2_i on organisatorischeeenheden (
   verantwoordelijke_mdw_id asc
);

/*==============================================================*/
/* Table: stappen                                               */
/*==============================================================*/
create table stappen  (
   id                   number(19)                      not null,
   zak_id               number(19)                      not null,
   verantwoordelijk_act_id number(19),
   verantwoordelijk_bar_id number(19),
   uitvoerder_act_id    number(19),
   uitvoerder_bar_id    number(19),
   oge_id               number(19)                      not null,
   begindatum           date                            not null,
   stapomschrijving     varchar2(80)                    not null,
   staptypecode         varchar2(10),
   stapvolgnummer       number(19),
   einddatum            date,
   einddatumgepland     date,
   normdoorlooptijd     number(19),
   procedurecode        varchar2(10),
   procedureomschrijving varchar2(80),
   rappeldatum          date,
   resultaatcode        varchar2(10),
   resultaatomschrijving varchar2(80),
   resultaattoelichting varchar2(1000),
   toelichting          varchar2(1000),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   huidigestap          varchar2(1)                    default 'N' not null
      constraint stp_huidigestap_ck check (huidigestap in ('J','N')) not deferrable,
   constraint stp_pk primary key (id) using index
);

comment on table stappen is
'STP';

/*==============================================================*/
/* Index: stp_act_fk_1_i                                        */
/*==============================================================*/
create index stp_act_fk_1_i on stappen (
   verantwoordelijk_act_id asc
)
;

/*==============================================================*/
/* Index: stp_act_fk_2_i                                        */
/*==============================================================*/
create index stp_act_fk_2_i on stappen (
   uitvoerder_act_id asc
)
;

/*==============================================================*/
/* Index: stp_bar_fk_1_i                                        */
/*==============================================================*/
create index stp_bar_fk_1_i on stappen (
   verantwoordelijk_bar_id asc
)
;

/*==============================================================*/
/* Index: stp_bar_fk_2_i                                        */
/*==============================================================*/
create index stp_bar_fk_2_i on stappen (
   uitvoerder_bar_id asc
)
;

/*==============================================================*/
/* Index: stp_zak_fk_i                                          */
/*==============================================================*/
create index stp_zak_fk_i on stappen (
   zak_id asc
)
;

/*==============================================================*/
/* Table: statussen                                             */
/*==============================================================*/
create table statussen  (
   id                   number(19)                      not null,
   zak_id               number(19)                      not null,
   zetter_act_id        number(19),
   zetter_bar_id        number(19),
   oge_id               number(19)                      not null,
   datumstatusgezet     date                            not null,
   statusvolgnummer     number(19)                      not null,
   statusomschrijving   varchar2(80)                    not null,
   statuscode           varchar2(10),
   statustoelichting    varchar2(1000),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   huidigestatus        char(1),
   constraint sta_pk primary key (id)
         using index
)
;

comment on table statussen is
'STA';

/*==============================================================*/
/* Index: sta_act_fk_i                                          */
/*==============================================================*/
create index sta_act_fk_i on statussen (
   zetter_act_id asc
)
;

/*==============================================================*/
/* Index: sta_bar_fk_i                                          */
/*==============================================================*/
create index sta_bar_fk_i on statussen (
   zetter_bar_id asc
)
;

/*==============================================================*/
/* Index: sta_zak_fk_i                                          */
/*==============================================================*/
create index sta_zak_fk_i on statussen (
   zak_id asc
)
;

/*==============================================================*/
/* Table: tmp_documenten                                        */
/*==============================================================*/
create table tmp_documenten  (
   id                   number(19)                      not null,
   oge_id               number(19)                      not null,
   bron_doc_id          number(19)                      not null,
   zaakcode             varchar2(25)                    not null,
   mimetype             varchar2(100)                   default 'application/octet-stream' not null,
   bestandsnaam         varchar2(256)                   not null,
   bestandsgrootte      number(19)                      not null,
   bestandsextentie     varchar2(25)                    not null,
   bestandsomschrijving varchar2(2000)                  not null,
   constraint pk_tdm primary key (id)
         using index
)
;

/*==============================================================*/
/* Table: tmp_zaaktypen                                         */
/*==============================================================*/
create global temporary table tmp_zaaktypen  (
   zte_id               number(19)                      not null,
   kolomnaam            varchar2(30)                    not null,
   waarde               varchar2(4000)
);

/*==============================================================*/
/* Table: zaakformulieren                                       */
/*==============================================================*/
create table zaakformulieren  (
   id                   number(19)                      not null,
   zaakidentificatie    varchar2(100),
   oge_id               number(19),
   inhoud               clob,
   datum                timestamp                      default systimestamp,
   constraint fmr_pk primary key (id)
         using index
)
;

comment on table zaakformulieren is
'ZFR';

/*==============================================================*/
/* Table: zaaktypen                                             */
/*==============================================================*/
create table zaaktypen  (
   id                   number(19)                      not null,
   oge_id               number(19)                      not null,
   naam                 varchar2(100)                   not null,
   omschrijving         varchar2(2000),
   publicatienaam       varchar2(100),
   publicatietekst      varchar2(4000),
   code_oeh             varchar2(50),
   doorlooptijd_gewenst number(19),
   doorlooptijd_wettelijk number(19),
   doorlooptijd_verlenging number(19),
   doorlooptijd_oranje  number(19),
   doorlooptijd_rood    number(19),
   tonen_gis            varchar2(1)                     default 'J' not null,
   tonen_gis_intakestatus varchar2(3)                   default 'ALS' not null,
   versie               number(10,1)                    not null,
   actief               varchar2(1)                     default 'J' not null,
   volgnummer           number(19)                      default 0,
   leges                number(19),
   bozaakcode           varchar2(1)                     default 'N',
   constraint zte_pk primary key (id)
         using index,
   constraint zte_uk unique (naam, versie, oge_id)
         using index
)
;

comment on table zaaktypen is
'ZTE';

/*==============================================================*/
/* Table: zaken                                                 */
/*==============================================================*/
create table zaken  (
   id                   number(19)                      not null,
   verantwoordelijke_act_id number(19),
   verantwoordelijke_bar_id number(19),
   initiator_act_id     number(19),
   initiator_bar_id     number(19),
   zaakidentificatie    varchar2(25)                    not null,
   startdatum           date                            not null,
   zaaktypecode         varchar2(10)                    not null,
   zaaktypeomschrijving varchar2(80)                    not null,
   oge_id               number(19)                      not null,
   doelgroep            varchar2(3)                     not null
      constraint zak_doelgroep_ck check (doelgroep in ('CLT','BDF')) not deferrable,
   einddatum            date,
   einddatumgepland     date,
   url                  varchar2(256),
   hyperlinkomschrijving varchar2(80),
   omschrijving         varchar2(80),
   kenmerk              varchar2(20),
   kenmerkbron          varchar2(40),
   resultaatcode        varchar2(10),
   resultaatomschrijving varchar2(80),
   resultaattoelichting varchar2(1000),
   toelichting          varchar2(1000),
   trefwoord            varchar2(100),
   uiterlijkeeinddatumafdoening date,
   bdf_bin              varchar2(60),
   bdf_unieke_code      varchar2(100),
   bdf_bsn_contactpersoon number(19),
   bsn                  number(19),
   anummer              number(19),
   aanmaakdatum         date                           default sysdate not null,
   aangemaakt_door      varchar2(100),
   zaaktype_versie      varchar2(30),
   zte_id               number(19),
   constraint zak_pk primary key (id)
         using index,
   constraint zak_uk unique (zaakidentificatie, oge_id)
         using index
)
;

comment on table zaken is
'ZAK';

/*==============================================================*/
/* Index: zak_act_fk_1_i                                        */
/*==============================================================*/
create index zak_act_fk_1_i on zaken (
   verantwoordelijke_act_id asc
)
;

/*==============================================================*/
/* Index: zak_act_fk_2_i                                        */
/*==============================================================*/
create index zak_act_fk_2_i on zaken (
   initiator_act_id asc
)
;

/*==============================================================*/
/* Index: zak_bar_fk_1_i                                        */
/*==============================================================*/
create index zak_bar_fk_1_i on zaken (
   verantwoordelijke_bar_id asc
)
;

/*==============================================================*/
/* Index: zak_bar_fk_2_i                                        */
/*==============================================================*/
create index zak_bar_fk_2_i on zaken (
   initiator_bar_id asc
)
;

/*==============================================================*/
/* Index: zak_zte_fk_1_i                                        */
/*==============================================================*/
create index zak_zte_fk_1_i on zaken (
   zte_id asc
)
;

/*==============================================================*/
/* Table: zakenmagazijn_logs                                    */
/*==============================================================*/
create table zakenmagazijn_logs  (
   module               varchar2(50)                    not null,
   log_type             varchar2(3)                     not null,
   stap                 number(19),
   note                 varchar2(4000),
   note_blob            blob,
   note_clob            clob,
   error_line           varchar2(2000),
   aangemaakt_door      varchar2(50),
   aanmaak_datum        timestamp                       default systimestamp not null
)
;

/*==============================================================*/
/* Table: zak_dmt                                               */
/*==============================================================*/
create table zak_dmt  (
   id                   number(19)                      not null,
   zak_id               number(19)                      not null,
   dmt_id               number(19)                      not null,
   oge_id               number(19)                      not null,
   aanmaakdatum         date                            default sysdate not null,
   aangemaakt_door      varchar2(100),
   constraint zdt_pk primary key (id)
         using index
       ,
   constraint zdt_uk unique (zak_id, dmt_id)
         using index
)
;

comment on table zak_dmt is
'ZDT';

/*==============================================================*/
/* Index: zdt_dmt_fk_i                                          */
/*==============================================================*/
create index zdt_dmt_fk_i on zak_dmt (
   dmt_id asc
)
;

/*==============================================================*/
/* Index: zdt_zak_fk_i                                          */
/*==============================================================*/
create index zdt_zak_fk_i on zak_dmt (
   zak_id asc
)
;

/*==============================================================*/
/* Table: adressen                                              */
/*==============================================================*/

create table adressen (
   id                   number(19)                      not null primary key,
   adresBuitenland1     varchar2(50),
   adresBuitenland2     varchar2(50),
   adresBuitenland3     varchar2(50),
   landcode             number(4,0),
   landnaam             varchar2(40),
   straatnaam           varchar2(24),
   huisnummer           number(5,0),
   huisletter           char(1),
   huisnummertoevoeging varchar2(5),
   aanduidingBijHuisnummer char(2),
   locatieomschrijving  varchar2(40),
   postcode             char(6),
   woonplaatsnaam       varchar2(24),
   postbusnummer        number(5,0),
   antwoordnummer       number(5,0),
   locatieadresnummer   number(12,0),
   gemeentecode         number(4,0)
);

/*==============================================================*/
/* Table: kadastrale_objecten                                   */
/*==============================================================*/

create table  kadastrale_objecten (
   id                   number(19)                      not null primary key,
   kadastraleGemeentecode varchar2(5),
   kadastraleSectie     char(2),
   kadastraalPerceelnummer number(5,0),
   kadastraalObjectIndexLetter char(1),
   kadastraalObjectIndexNummer number(4,0)
);


/*==============================================================*/
/* Table: verblijfsobjecten                                     */
/*==============================================================*/
create table verblijfsobjecten (
   id                   number(19)                      not null primary key,
   bouwjaar             number(4,0),
   verblijfsobjectnummer number(12,0),
   verblijfsobjecttype  number(3,0),
   heeft                number(19)                      references adressen(id)
);


/*==============================================================*/
/* Table: locaties                                              */
/*==============================================================*/
create table locaties (
   id                   number(19)                      not null primary key,
   locatie_type         varchar2(3)                     not null
);


/*==============================================================*/
/* Table: zaken_adressen                                        */
/*==============================================================*/
create table zaken_adressen (
   zak_id               number(19)                      references zaken(id),
   ads_id               number(19)                      references adressen(id)
);

/*==============================================================*/
/* Table: zaken_kadobjecten                                     */
/*==============================================================*/
create table zaken_kadobjecten (
   zak_id               number(19)                      references zaken(id),
   kot_id               number(19)                      references kadastrale_objecten(id)
);

/*==============================================================*/
/* Table: zaken_verblijfsobjecten                               */
/*==============================================================*/
create table zaken_verblijfsobjecten (
   zak_id               number(19)                      references zaken(id),
   vbo_id               number(19)                      references verblijfsobjecten(id)
);

/*==============================================================*/
/* Table: zaakkenmerken                                         */
/*==============================================================*/
create table zaakkenmerken ( 
   id                   number(19)                      not null primary key,
   zak_id               number(19)                      not null references zaken(id),
   kenmerk              varchar2(20)                    not null,
   kenmerkbron          varchar2(40)                    not null
);

/*==============================================================*/
/* Table: zaakhyperlinks                                        */
/*==============================================================*/
create table zaakhyperlinks (
   id                   number(19)                      not null primary key,
   zak_id               number(19)                      not null references zaken(id),
   url                  varchar2(255)                   not null,
   omschrijving         varchar2(80)
);

/*==============================================================*/
/* Table: zaaktrefwoorden                                       */
/*==============================================================*/
create table zaaktrefwoorden (
   id                   number(19)                      not null primary key,
   zak_id               number(19)                      not null references zaken(id),
   trefwoord            varchar2(1000)                  not null
);

/*==============================================================*/
/* Table: verzoeken                                             */
/*==============================================================*/
create table verzoeken (
   id                   number(19)                      not null,
   aanvrager            varchar2(80),
   datumafgehandeld     date,
   datumindiening       date                            not null,
   verantwoordelijke    varchar2(40),
   verzoekidentificatie varchar2(25)                    not null,
   verzoekomschrijving  varchar2(80)                    not null,
   verzoektoelichting   varchar2(992),
   act_id_ini           number(19),
   act_id_vaw           number(19),
   bdf_bin              varchar2(60),
   bdf_unieke_code      varchar2(100),
   bdf_bsn_contactpersoon number(19),
   bsn                  number(19),
   anummer              number(19),
   aanmaakdatum         date                            default sysdate not null,
   aangemaakt_door      varchar2(100),
   oge_id               number(19)                      not null,
   constraint verz_pk primary key (id)
         using index,
   constraint verz_uk unique (verzoekidentificatie, oge_id)
         using index
);

alter table verzoeken add constraint vrz_act_ini_fk foreign key (act_id_ini) references actoren (id);
alter table verzoeken add constraint vrz_act_vaw_fk foreign key (act_id_vaw) references actoren (id);

create index vrz_act_ini_fk_i on verzoeken (act_id_ini);
create index vrz_act_vaw_fk_i on verzoeken (act_id_vaw);

comment on table verzoeken is 'Een door de burger en niet natuurlijk persoon als een logisch geheel beschouwde hoeveelheid werk, die de gemeente niet in één zaak kan afhandelen, maar opsplitst in meerdere zaken.';
comment on column verzoeken.id is 'Unieke identificatie.';
comment on column verzoeken.aanvrager is 'De opgemaakte naam van het subject dat het verzoek heeft ingediend.';
comment on column verzoeken.datumafgehandeld is 'De datum waarop het verzoek is afgehandeld.';
comment on column verzoeken.datumindiening is 'De datum waarop het verzoek is ingediend.';
comment on column verzoeken.verantwoordelijke is 'De opgemaakte naam van de verantwoordelijke medewerker of organisatorische eenheid voor het verzoek.';
comment on column verzoeken.verzoekidentificatie is 'Een code die het verzoek identificeert.';
comment on column verzoeken.verzoekomschrijving is 'Een korte omschrijving van het verzoek.';
comment on column verzoeken.verzoektoelichting is 'Een toelichting op het verzoek.';
comment on column verzoeken.act_id_ini is 'VERZOEK is geinitieerd door ACTOR';
comment on column verzoeken.act_id_vaw is 'VERZOEK heeft als verantwoordelijke ACTOR';
comment on column verzoeken.bdf_bin is 'bdf_bin';
comment on column verzoeken.bdf_unieke_code is 'bdf_unieke_code';
comment on column verzoeken.bdf_bsn_contactpersoon is 'bdf_bsn_contactpersoon';
comment on column verzoeken.bsn is 'bsn';
comment on column verzoeken.anummer is 'anummer';
comment on column verzoeken.aanmaakdatum is 'Datum waarop record is toegevoegd aan tabel.';
comment on column verzoeken.aangemaakt_door is 'Identificatie van toevoegende gebruiker.';


/*==============================================================*/
/* Table: verzoekformulieren                                               */
/*==============================================================*/
create table verzoekformulieren (
   id                   number(19)                      not null primary key,
   verzoekidentificatie varchar2(25)                    not null,
   oge_id               number(19),
   inhoud               clob,
   datum                timestamp(6)                    default systimestamp
);

alter table verzoekformulieren add constraint vfr_oge_fk foreign key (oge_id) references organisatorischeeenheden (id);

create index vfr_oge_fk_i on verzoekformulieren (oge_id);

comment on table verzoekformulieren is 'VFR';
comment on column verzoekformulieren.id is 'Unieke identificatie.';
comment on column verzoekformulieren.verzoekidentificatie is 'Verzoekidentificatie';
comment on column verzoekformulieren.oge_id is 'Referentie naar organisatorische eenheden';
comment on column verzoekformulieren.inhoud is 'Inhoud';
comment on column verzoekformulieren.datum is 'Datum';

/*==============================================================*/
/* Table: verzoeken_zaken                                               */
/*==============================================================*/
create table verzoeken_zaken (
   id                   number(19)                      not null primary key,
   vrz_id               number(19)                      not null,
   zak_id               number(19)                      not null,
   aanmaakdatum         date                            default sysdate not null,
   aangemaakt_door      varchar2(100),
   oge_id               number(19)                      not null
);

alter table verzoeken_zaken add constraint vzk_vrz_fk foreign key (vrz_id) references verzoeken (id);
alter table verzoeken_zaken add constraint vzk_zak_fk foreign key (zak_id) references zaken (id);

create index vzk_vrz_fk_i on verzoeken_zaken (vrz_id);
create index vzk_zak_fk_i on verzoeken_zaken (zak_id);

comment on table verzoeken_zaken is 'VERZOEK heeft als onderdeel ZAAK';
comment on column verzoeken_zaken.id is 'Unieke identificatie.';
comment on column verzoeken_zaken.vrz_id is 'Verwijzing naar verzoek';
comment on column verzoeken_zaken.zak_id is 'Verwijzing naar zaak';
comment on column verzoeken_zaken.aanmaakdatum is 'Datum waarop record is toegevoegd aan tabel.';
comment on column verzoeken_zaken.aangemaakt_door is 'Identificatie van toevoegende gebruiker.';

alter table actoren
   add constraint act_mdw_fk foreign key (mdw_id)
      references medewerkers (id)
      not deferrable;

alter table actoren
   add constraint act_oeh_fk foreign key (oeh_id)
      references organisatorischeeenheden (id)
      not deferrable;

alter table gerelateerdezaken
   add constraint gzk_zak_fk_1 foreign key (zak_hoofd_id)
      references zaken (id)
      not deferrable;

alter table gerelateerdezaken
   add constraint gzk_zak_fk_2 foreign key (zak_sub_id)
      references zaken (id)
      not deferrable;

alter table organisatorischeeenheden
   add constraint oeh_mdw_fk_1 foreign key (contactpersoon_mdw_id)
      references medewerkers (id)
      not deferrable;

alter table organisatorischeeenheden
   add constraint oeh_mdw_fk_2 foreign key (verantwoordelijke_mdw_id)
      references medewerkers (id)
      not deferrable;

alter table stappen
   add constraint stp_act_fk_1 foreign key (verantwoordelijk_act_id)
      references actoren (id)
      not deferrable;

alter table stappen
   add constraint stp_act_fk_2 foreign key (uitvoerder_act_id)
      references actoren (id)
      not deferrable;

alter table stappen
   add constraint stp_bar_fk_1 foreign key (verantwoordelijk_bar_id)
      references beperkte_actoren (id)
      not deferrable;

alter table stappen
   add constraint stp_bar_fk_2 foreign key (uitvoerder_bar_id)
      references beperkte_actoren (id)
      not deferrable;

alter table stappen
   add constraint stp_zak_fk foreign key (zak_id)
      references zaken (id)
      not deferrable;

alter table statussen
   add constraint sta_act_fk foreign key (zetter_act_id)
      references actoren (id)
      not deferrable;

alter table statussen
   add constraint sta_bar_fk foreign key (zetter_bar_id)
      references beperkte_actoren (id)
      not deferrable;

alter table statussen
   add constraint sta_zak_fk foreign key (zak_id)
      references zaken (id)
      not deferrable;

alter table zaken
   add constraint zak_act_fk_1 foreign key (verantwoordelijke_act_id)
      references actoren (id)
      not deferrable;

alter table zaken
   add constraint zak_act_fk_2 foreign key (initiator_act_id)
      references actoren (id)
      not deferrable;

alter table zaken
   add constraint zak_bar_fk_1 foreign key (verantwoordelijke_bar_id)
      references beperkte_actoren (id)
      not deferrable;

alter table zaken
   add constraint zak_bar_fk_2 foreign key (initiator_bar_id)
      references beperkte_actoren (id)
      not deferrable;

alter table zaken
   add constraint zak_zte_fk_1 foreign key (zte_id)
      references zaaktypen (id)
      not deferrable;

alter table zak_dmt
   add constraint zdt_dmt_fk foreign key (dmt_id)
      references documenten (id)
      not deferrable;

alter table zak_dmt
   add constraint zdt_zak_fk foreign key (zak_id)
      references zaken (id)
      not deferrable;
