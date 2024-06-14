Create table tbTickete(
UUID varchar2(50),
titulo varchar2(50),
descripcion varchar2(150),
autor varchar2(50),
email varchar2(50),
fechaInicio varchar2(50),
estado varchar2(50),
fechaFinal varchar2(50)
);

CREATE TABLE tbUsuarios(
    UUID_usuario VARCHAR2(50),
    correoElectronico VARCHAR2(50),
    clave VARCHAR2(50)
);

select * from tbTickete