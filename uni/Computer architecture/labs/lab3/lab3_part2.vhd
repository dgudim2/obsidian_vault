library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab3_part2 is
	Port (
		i_bit  : in  STD_LOGIC_VECTOR(0 to 3);
		o_bit0 : out STD_LOGIC;
		o_sign : out STD_LOGIC_VECTOR(0 to 1)
	);
end lab3_part2;

architecture behave of lab3_part2 is
	signal sign : STD_LOGIC_VECTOR(0 to 1);
	begin
		sign(0)  <= i_bit(0) nor i_bit(1);
		sign(1)  <= i_bit(2) and i_bit(3);
		
		o_sign <= sign;
		o_bit0 <= sign(0) nand sign(1);
	end behave;