library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity lab4_mux is
	Port (
		IN1 : in  STD_LOGIC_VECTOR(0 to 2);
		IN2 : in  STD_LOGIC_VECTOR(0 to 2);
		IN3 : in  STD_LOGIC_VECTOR(0 to 2);
		IN4 : in  STD_LOGIC_VECTOR(0 to 2);
		
		MUX_OUT : out STD_LOGIC_VECTOR(0 to 2);
		SEL     : in  STD_LOGIC_VECTOR(0 to 1)
	);
end lab4_mux;

architecture mux of lab4_mux is
	begin
		process (SEL, IN1, IN2, IN3, IN4)
		begin
			case SEL is
				when "00" => MUX_OUT <= IN1;
				when "01" => MUX_OUT <= IN2;
				when "10" => MUX_OUT <= IN3;
				when "11" => MUX_OUT <= IN4;
			end case;
		end process;
	end mux;