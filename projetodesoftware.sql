-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 17-Jul-2019 às 07:29
-- Versão do servidor: 10.1.37-MariaDB
-- versão do PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projetodesoftware`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_cliente`
--

CREATE TABLE `tbl_cliente` (
  `pk_id_cliente` bigint(20) UNSIGNED NOT NULL,
  `cli_nome` varchar(300) NOT NULL,
  `cli_endereco` varchar(350) NOT NULL,
  `cli_bairro` varchar(300) NOT NULL,
  `cli_cidade` varchar(200) NOT NULL,
  `cli_uf` varchar(2) NOT NULL,
  `cli_cep` varchar(9) NOT NULL,
  `cli_telefone` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tbl_cliente`
--

INSERT INTO `tbl_cliente` (`pk_id_cliente`, `cli_nome`, `cli_endereco`, `cli_bairro`, `cli_cidade`, `cli_uf`, `cli_cep`, `cli_telefone`) VALUES
(2, 'Lu', 'afasf', 'asfsf', 'afsf', 'MG', 'ssfaf', 'sfasff'),
(4, 'nome', 'eeer', 'erer', 'erer', 'gf', 'sffs', 'sgsggsdg'),
(5, 'sfas', 'assfaf', 'safsaf', 'asfs', 'fg', 'asfasf', 'afsf'),
(6, 'gjg', 'gjgj', 'gjgjgjj', 'ggjgjg', 'LK', '634646', '3456675754');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_produto`
--

CREATE TABLE `tbl_produto` (
  `pk_id_produto` bigint(20) UNSIGNED NOT NULL,
  `pro_nome` varchar(300) NOT NULL,
  `pro_valor` double NOT NULL,
  `pro_estoque` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tbl_produto`
--

INSERT INTO `tbl_produto` (`pk_id_produto`, `pro_nome`, `pro_valor`, `pro_estoque`) VALUES
(17, 'ttt', 333, 333),
(18, 'uuu', 676, 4545);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_usuario`
--

CREATE TABLE `tbl_usuario` (
  `pk_id_usuario` bigint(20) UNSIGNED NOT NULL,
  `usu_nome` varchar(150) NOT NULL,
  `usu_login` varchar(100) NOT NULL,
  `usu_senha` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `tbl_usuario`
--

INSERT INTO `tbl_usuario` (`pk_id_usuario`, `usu_nome`, `usu_login`, `usu_senha`) VALUES
(1, 'Luan', 'luanmg2', 123),
(11, 'sdsds', 'sdsd', 123);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_vendas`
--

CREATE TABLE `tbl_vendas` (
  `pk_id_vendas` bigint(20) UNSIGNED NOT NULL,
  `fk_cliente` bigint(20) UNSIGNED NOT NULL,
  `ven_data_venda` date NOT NULL,
  `ven_valor_liquido` double NOT NULL,
  `ven_valor_bruto` double NOT NULL,
  `ven_desconto` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbl_vendas_produtos`
--

CREATE TABLE `tbl_vendas_produtos` (
  `pk_id_venda_produto` bigint(20) UNSIGNED NOT NULL,
  `fk_produto` bigint(20) UNSIGNED NOT NULL,
  `fk_vendas` bigint(20) UNSIGNED NOT NULL,
  `ven_pro_valor` double NOT NULL,
  `ven_pro_quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_cliente`
--
ALTER TABLE `tbl_cliente`
  ADD UNIQUE KEY `id_cliente` (`pk_id_cliente`);

--
-- Indexes for table `tbl_produto`
--
ALTER TABLE `tbl_produto`
  ADD UNIQUE KEY `pk_id_produto` (`pk_id_produto`);

--
-- Indexes for table `tbl_usuario`
--
ALTER TABLE `tbl_usuario`
  ADD UNIQUE KEY `id_usuario` (`pk_id_usuario`);

--
-- Indexes for table `tbl_vendas`
--
ALTER TABLE `tbl_vendas`
  ADD UNIQUE KEY `PRYMARY` (`pk_id_vendas`) USING BTREE,
  ADD KEY `fkcliente` (`fk_cliente`);

--
-- Indexes for table `tbl_vendas_produtos`
--
ALTER TABLE `tbl_vendas_produtos`
  ADD UNIQUE KEY `PRYMARY` (`pk_id_venda_produto`) USING BTREE,
  ADD KEY `fkprodutos` (`fk_produto`),
  ADD KEY `fkvendas` (`fk_vendas`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_cliente`
--
ALTER TABLE `tbl_cliente`
  MODIFY `pk_id_cliente` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_produto`
--
ALTER TABLE `tbl_produto`
  MODIFY `pk_id_produto` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `tbl_usuario`
--
ALTER TABLE `tbl_usuario`
  MODIFY `pk_id_usuario` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tbl_vendas`
--
ALTER TABLE `tbl_vendas`
  MODIFY `pk_id_vendas` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_vendas_produtos`
--
ALTER TABLE `tbl_vendas_produtos`
  MODIFY `pk_id_venda_produto` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `tbl_vendas`
--
ALTER TABLE `tbl_vendas`
  ADD CONSTRAINT `tbl_vendas_ibfk_1` FOREIGN KEY (`fk_cliente`) REFERENCES `tbl_cliente` (`pk_id_cliente`);

--
-- Limitadores para a tabela `tbl_vendas_produtos`
--
ALTER TABLE `tbl_vendas_produtos`
  ADD CONSTRAINT `tbl_vendas_produtos_ibfk_1` FOREIGN KEY (`fk_produto`) REFERENCES `tbl_produto` (`pk_id_produto`),
  ADD CONSTRAINT `tbl_vendas_produtos_ibfk_2` FOREIGN KEY (`fk_vendas`) REFERENCES `tbl_vendas` (`pk_id_vendas`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
