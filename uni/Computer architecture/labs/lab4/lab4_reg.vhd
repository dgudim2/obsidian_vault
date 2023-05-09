library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab4_reg is
	Port (
		IN0   : in  STD_LOGIC_VECTOR(0 to 5);
		LOAD  : in  STD_LOGIC;
		CLK1  : in  STD_LOGIC;
		
		OUT0  : out STD_LOGIC_VECTOR(0 to 5)
	);
end lab4_reg;

architecture behave of lab4_reg is

	signal STORAGE : STD_LOGIC_VECTOR(0 to 5) := "000000";

	begin
	
		process(CLK1)
		begin
			if (falling_edge(CLK1)) then
				if (LOAD = '0') then
					STORAGE <= IN0;
				end if;
			end if;
		end process;
	
	OUT0 <= STORAGE;
	
	end behave;