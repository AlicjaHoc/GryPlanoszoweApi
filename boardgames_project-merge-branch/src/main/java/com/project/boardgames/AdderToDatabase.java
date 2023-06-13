package com.project.boardgames;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.boardgames.entities.Address;
import com.project.boardgames.entities.AppUser;
import com.project.boardgames.entities.Producer;
import com.project.boardgames.entities.Product;
import com.project.boardgames.entities.Role;
import com.project.boardgames.repositories.AddressRepository;
import com.project.boardgames.repositories.AppUserRepository;
import com.project.boardgames.repositories.ProducerRepository;
import com.project.boardgames.repositories.ProductRepository;
import com.project.boardgames.utilities.authentication.PasswordHandler;

@Component
public class AdderToDatabase implements ApplicationRunner {

    @Autowired ProductRepository productRepository;
    @Autowired ProducerRepository producerRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired AppUserRepository userRepository;
    @Autowired PasswordHandler passwordEncoder;
    

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        Producer producer1 = new Producer("PANS Krosno");
        producerRepository.save(producer1);

        productRepository.save(new Product("Labirynt Zegarów", "Gra, w której gracze muszą pokonać labirynt zegarów, zanim czas się skończy.", producer1, BigDecimal.valueOf(12.50), "5sekund.png"));

        productRepository.save(new Product("Kopalnia Skarbów", "Gra, w której gracze muszą wydobyć jak najwięcej skarbów z kopalni, zanim zostaną zasypane.", producer1, BigDecimal.valueOf(20.72), "Bierki.jpg"));
        
        productRepository.save(new Product("Wojna Smoków", "Gra, w której gracze rywalizują o władzę nad królestwem smoków.", producer1, BigDecimal.valueOf(140.50), "7b2263a0957e4bcfabe0d8a5aee97b06.jpg"));

        
        productRepository.save(new Product("Tropiciel", "Gra, w której gracze muszą odnaleźć ukryte przedmioty w labiryntach.", producer1, BigDecimal.valueOf(120.50), "Beczka_smiechu.png"));

        
        productRepository.save(new Product("Karciany Rycerz", "Gra karciana, w której gracze rywalizują o miano największego rycerza w królestwie.", producer1, BigDecimal.valueOf(77.10), "Bingo.png"));

        
        productRepository.save(new Product("Słodkie Receptury", " Gra, w której gracze rywalizują o to, kto ugotuje najlepsze ciasto.", producer1, BigDecimal.valueOf(53.20), "było-sobie-życie.jpg"));

        
        productRepository.save(new Product("Wyprawa po Skarby", "Gra, w której gracze muszą przemierzyć niebezpieczne terytoria, aby odnaleźć skarby.", producer1, BigDecimal.valueOf(31.75), "chińczyk.jpg"));

        
        productRepository.save(new Product("Bitwa o Władzę", "Gra strategiczna, w której gracze rywalizują o kontrolę nad miastami i królestwami.", producer1, BigDecimal.valueOf(56.50), "chińczyk1.jpg"));

        
        productRepository.save(new Product("Fantastyczny Świat", "Gra przygodowa, w której gracze muszą odkryć sekrety tajemniczego świata.", producer1, BigDecimal.valueOf(94.53), "db7a9c5a77ad4293bcbc93d4ae2e2198.jpg"));

        
        productRepository.save(new Product("Port Royal", "Gra karciana, w której gracze rywalizują o władzę nad portem.", producer1, BigDecimal.valueOf(111.70), "Dixit.png"));

        
        productRepository.save(new Product("Osadnicy z Catanu", "Gra strategiczna, w której gracze rywalizują o zasoby, aby zbudować najlepszą osadę.", producer1, BigDecimal.valueOf(45.70), "catan-miasta-i-rycerze.jpg"));

        
        productRepository.save(new Product("Pogromcy Potworów", "Gra przygodowa, w której gracze muszą walczyć z potworami i pokonywać niebezpieczeństwa.", producer1, BigDecimal.valueOf(9.99), "domino-2.jpg"));

        
        productRepository.save(new Product("Piraci z Karaibów", "Gra, w której gracze rywalizują o kontrolę nad morzem i skarbami.", producer1, BigDecimal.valueOf(76.92), "Domino.jpg"));

        
        productRepository.save(new Product("Odkrywca", "Gra, w której gracze muszą odkryć tajemnice dalekich krain i zdobyć wiedzę.", producer1, BigDecimal.valueOf(65.50), "Dylemat-wagonika-2.png"));

        
        productRepository.save(new Product("Ostatnia Nadzieja", "Gra kooperacyjna, w której gracze muszą razem przeżyć apokalipsę i przetrwać.", producer1, BigDecimal.valueOf(87.90), "Dylemat-wagonika.png"));

        
        productRepository.save(new Product("Magia Świata", "Gra karciana, w której gracze rywalizują o władzę nad magicznym światem.", producer1, BigDecimal.valueOf(112.50), "dylematy.jpg"));

        
        productRepository.save(new Product("Tajemnicze Ruiny", "Gra, w której gracze muszą odkryć tajemnice starożytnych ruin i odnaleźć skarby.", producer1, BigDecimal.valueOf(121.50), "eurobiznes.jpg"));

        
        productRepository.save(new Product("Wyścig Kryształowych Smoków", "Gra, w której gracze muszą rywalizować o władzę nad smoczymi kryształami.", producer1, BigDecimal.valueOf(45.50), "Gierki-Malzenskie.png"));

        
        productRepository.save(new Product("Śmierć Na Ringu", "Gra, w której gracze muszą walczyć ze sobą w brutalnych pojedynkach na arenie.", producer1, BigDecimal.valueOf(34.10), "Gra-z-dzwonkiem.png"));

        
        productRepository.save(new Product("Powrót do Przeszłości", "Gra, w której gracze muszą cofnąć się w czasie i zmieniać historię, aby uniknąć katastrofy.", producer1, BigDecimal.valueOf(76.50), "Jenga.png"));

        
        productRepository.save(new Product("Miasto Marzeń", "Gra, w której gracze muszą budować swoje własne miasta i realizować swoje marzenia.", producer1, BigDecimal.valueOf(142.50), "jumanji.jpg"));

        
        productRepository.save(new Product("Wyspa Zaginionych Skarbów", "Gra, w której gracze muszą odnaleźć zaginione skarby na tajemniczej wyspie.", producer1, BigDecimal.valueOf(43.80), "karty gentlemanów.jpg"));

        
        productRepository.save(new Product("Kryptoanaliza", "Gra logiczna, w której gracze muszą rozwiązywać zagadki i odkrywać tajemnice.", producer1, BigDecimal.valueOf(78.43), "Kiciusie.png"));

        
        productRepository.save(new Product("Bunt Robotów", "Gra, w której gracze muszą walczyć z rebelią robotów i uratować ludzkość.", producer1, BigDecimal.valueOf(12.50), "łąka.jpg"));

        
        productRepository.save(new Product("Wyprawa do Złotej Doliny", "Gra, w której gracze muszą przemierzyć niebezpieczne tereny, aby odnaleźć ukryte skarby.", producer1, BigDecimal.valueOf(42.50), "monopoly.jpg"));

        
        productRepository.save(new Product("Galaktyczne Imperium", "Gra strategiczna, w której gracze rywalizują o władzę nad galaktyką.", producer1, BigDecimal.valueOf(12.50), "Nogi Stonogi.png"));

        
        productRepository.save(new Product("Tajemnicze Złote Miasto", "Gra przygodowa, w której gracze muszą odnaleźć legendarne złote miasto.", producer1, BigDecimal.valueOf(112.50), "Osadnicy.jpg"));

        
        productRepository.save(new Product("Wojownicy Światła", "Gra, w której gracze muszą walczyć z siłami ciemności i przywrócić światło.", producer1, BigDecimal.valueOf(31.30), "Pionki-chinczyk.jpg"));

        
        productRepository.save(new Product("Zaginiony Świat Dinozaurów", "Gra, w której gracze muszą odkryć zaginiony świat dinozaurów i przetrwać.", producer1, BigDecimal.valueOf(65.50), "podejrzany.jpg"));

        
        productRepository.save(new Product("Kryminalne Zagadki", "Gra, w której gracze muszą rozwiązywać kryminalne zagadki i złapać przestępców.", producer1, BigDecimal.valueOf(21.50), "Polska.jpg"));

        
        productRepository.save(new Product("Złota Gorączka", "Gra, w której gracze muszą rywalizować o złoto i bogactwo w czasach gorączki złota.", producer1, BigDecimal.valueOf(17.50), "Poznaj europe.png"));

        
        productRepository.save(new Product("Podróż do Wymarzonej Krainy", "Gra, w której gracze muszą przemierzyć nieznane tereny, aby dotrzeć do wymarzonej krainy.", producer1, BigDecimal.valueOf(65.50), "scrabble.jpg"));

        
        productRepository.save(new Product("Pirackie Skarby", "Gra, w której gracze muszą odnaleźć ukryte skarby pirackie na morzu.", producer1, BigDecimal.valueOf(34.23), "Skaczace jajeczka.png"));

        
        productRepository.save(new Product("Władca Królestwa", "Gra strategiczna, w której gracze rywalizują o władzę nad królestwem.", producer1, BigDecimal.valueOf(64.50), "Smoki.png"));

        
        productRepository.save(new Product("Labirynt Skarbów", "Gra, w której gracze muszą pokonać labirynt i zdobyć skarby.", producer1, BigDecimal.valueOf(32.21), "Skaczace_malpki.png"));

        
        productRepository.save(new Product("Magiczny Portal", "Gra, w której gracze muszą odkryć tajemnice magicznego portalu i przejść na drugą stronę.", producer1, BigDecimal.valueOf(112.50), "Statki.png"));

        
        productRepository.save(new Product("Tajemnicza Wyspa", "Gra przygodowa, w której gracze muszą odkryć tajemnice tajemniczej wyspy.", producer1, BigDecimal.valueOf(54.50), "Szachy.png"));

        
        productRepository.save(new Product("Bitwa o Marsa", "Gra, w której gracze rywalizują o kontrolę nad planetą Mars.", producer1, BigDecimal.valueOf(12.50), "Tabliczka-mnozenia.png"));

        
        productRepository.save(new Product("Nieskończona Przygoda", "Gra, w której gracze muszą przemierzyć niekończące się krainy i odkryć nowe światy.", producer1, BigDecimal.valueOf(99.99), "Twister.png"));

        
        productRepository.save(new Product("Wyspa Zombie", "Gra, w której gracze muszą przetrwać na tajemniczej wyspie pełnej zombie i zbudować schronienie przed nimi.", producer1, BigDecimal.valueOf(111.50), "UFO POR---Dron.png"));

        
        productRepository.save(new Product("Świat Magii", "Gra, w której gracze muszą odkryć tajemnice magicznego świata i nauczyć się czarować.", producer1, BigDecimal.valueOf(12.50), "Uno.jpg"));

        
        productRepository.save(new Product("Starożytne Ruiny", "Gra przygodowa, w której gracze muszą odkryć tajemnice starożytnych ruin i odnaleźć ukryte skarby.", producer1, BigDecimal.valueOf(55.99), "Wsiasc-do-pociągu.png"));

        
        productRepository.save(new Product("Inwazja Obcych", "Gra, w której gracze muszą walczyć z inwazją obcych i obronić Ziemię.", producer1, BigDecimal.valueOf(43.50), "zgadnij kto to.png"));

        
        productRepository.save(new Product("Miasto Przestępców", "Gra, w której gracze muszą walczyć z przestępczością i przywrócić porządek w mieście.", producer1, BigDecimal.valueOf(74.15), "Zaginiona wyspa Arnak.png"));

        
        productRepository.save(new Product("Sekrety Egipskiej Piramidy", "Gra przygodowa, w której gracze muszą odkryć tajemnice egipskiej piramidy i zdobyć skarby.", producer1, BigDecimal.valueOf(12.50), "Uno.jpg"));

        
        productRepository.save(new Product("Czarodziejska Gra", "Gra, w której gracze muszą walczyć z czarnoksiężnikami i odnaleźć magiczny artefakt.", producer1, BigDecimal.valueOf(22.50), "Smoki.png"));

        
        productRepository.save(new Product("Olimpijskie Zawody", "Gra, w której gracze rywalizują w różnych dyscyplinach sportowych i zdobywają złoto olimpijskie.", producer1, BigDecimal.valueOf(110.45), "Statki.png"));

        
        productRepository.save(new Product("Skradzione Dzieła Sztuki", "Gra, w której gracze muszą odzyskać skradzione dzieła sztuki i złapać złodziei.", producer1, BigDecimal.valueOf(28.23), "scrabble.jpg"));

        
        productRepository.save(new Product("Zimowa Wyprawa", "Gra, w której gracze muszą przetrwać na zimowej wyprawie i zdobyć szczyt góry.", producer1, BigDecimal.valueOf(89.90), "Smoki.png"));

        
        productRepository.save(new Product("Bitwa o Tron", "Gra strategiczna, w której gracze rywalizują o władzę nad królestwem i walczą o tron.", producer1, BigDecimal.valueOf(250.50), "Uno.jpg"));

        AppUser user1 = new AppUser("Agnieszka", "Akademicka", "akademicka@pans.pl", passwordEncoder.encrypt("password123"), Role.USER, true, new Address("Wyspiańskiego 20", "Krosno", "Podkarpackie", "38-400", "Polska"));
        userRepository.save(user1);
        AppUser user2 = new AppUser("Michał", "Adamczyk", "adamczyk@pans.pl", passwordEncoder.encrypt("password123"), Role.USER, true, new Address("Akademicka 50/12", "Rzeszów", "Mazowieckie", "35-112", "Polska"));
        userRepository.save(user2);
        AppUser user3 = new AppUser("Dominika", "Fajowska", "fajowska@pans.pl", passwordEncoder.encrypt("password123"), Role.USER, true, new Address("Michalicka 12", "Górki", "Podkarpackie", "36-100", "Polska"));
        userRepository.save(user3);
        




    }
    
}
