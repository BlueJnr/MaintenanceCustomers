CREATE PROCEDURE ins_customer(in _surnames VARCHAR(50),in _names VARCHAR(50), in _email VARCHAR(50), in _telephone VARCHAR(50))
     insert into clientes (apellidos, nombres, correo, telefono) values(_surnames,_names,_email,_telephone);

CREATE PROCEDURE upd_customer(in idCustomer INT, in _surnames VARCHAR(50),in _names VARCHAR(50), in _email VARCHAR(50), in _telephone VARCHAR(50))
     update clientes set apellidos=_surnames, nombres=_names, correo=_email, telefono=_telephone where idcliente=idCustomer;

CREATE PROCEDURE del_customer(in idCustomer VARCHAR(3))
     delete from clientes where idcliente=idCustomer;

CREATE PROCEDURE findAll_customer()
     select * from clientes;

CREATE PROCEDURE find_customer(in idCustomer VARCHAR(3))
     select * from clientes where idcliente=idCustomer;

CREATE PROCEDURE findBySurnamesAndNames_customer(in _surnames VARCHAR(50),in _names VARCHAR(50))
     select * from clientes where apellidos=_surnames and nombres=_names;


