library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab1 is
	Port (
		i_bit0 : in  STD_LOGIC;
		i_bit1 : in  STD_LOGIC;
		i_bit2 : in  STD_LOGIC;
		i_bit3 : in  STD_LOGIC;
		o_bit0 : out STD_LOGIC;
		o_bit1 : out STD_LOGIC
	);
end lab1;

architecture behave of lab1 is
	begin
		o_bit0 <= i_bit0 and i_bit1;
		o_bit1 <= i_bit2 xor i_bit3;
	end behave;