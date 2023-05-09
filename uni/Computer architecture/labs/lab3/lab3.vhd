library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab3 is
Port ( 
	i_bits : in STD_LOGIC_VECTOR(0 to 2);
	o_bits : out STD_LOGIC_VECTOR(0 to 2)
	);
	
end lab3;
architecture behave of lab3 is
begin
	o_bits(0) <= i_bits(0);
	o_bits(1 to 2) <= i_bits(1 to 2);
end behave;