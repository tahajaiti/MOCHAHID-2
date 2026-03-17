package com.mochahid.videoservice.config;

import com.mochahid.videoservice.entity.Category;
import com.mochahid.videoservice.entity.Video;
import com.mochahid.videoservice.entity.VideoType;
import com.mochahid.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final VideoRepository videoRepository;

    @Override
    public void run(String... args) {
        if (videoRepository.count() > 0) return;

        List<Video> videos = List.of(
                Video.builder()
                        .title("Inception")
                        .description("A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/ljsZTbVsrQSqZgWeep2B1QiDKuh.jpg")
                        .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                        .duration(148).releaseYear(2010).type(VideoType.FILM).category(Category.SCIENCE_FICTION)
                        .rating(8.8).director("Christopher Nolan")
                        .cast(List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page", "Tom Hardy"))
                        .views(15420).featured(true).build(),

                Video.builder()
                        .title("Breaking Bad")
                        .description("A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/ggFHVNu6YYI5L9pCfOacjizRGt.jpg")
                        .trailerUrl("https://www.youtube.com/embed/HhesaQXLuRY")
                        .duration(49).releaseYear(2008).type(VideoType.SERIES).category(Category.DRAMA)
                        .rating(9.5).director("Vince Gilligan")
                        .cast(List.of("Bryan Cranston", "Aaron Paul", "Anna Gunn", "Dean Norris"))
                        .views(28900).featured(true).build(),

                Video.builder()
                        .title("Planet Earth II")
                        .description("David Attenborough returns to present this groundbreaking series exploring the wildlife of the world.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/6XWJv8mGJt9K6L8snCJBzHwu7XP.jpg")
                        .trailerUrl("https://www.youtube.com/embed/c8aFcHFu8QM")
                        .duration(50).releaseYear(2016).type(VideoType.DOCUMENTARY).category(Category.DOCUMENTARY)
                        .rating(9.4).director("David Attenborough")
                        .cast(List.of("David Attenborough"))
                        .views(12300).featured(true).build(),

                Video.builder()
                        .title("The Dark Knight")
                        .description("When the menace known as the Joker wreaks havoc on Gotham, Batman must accept one of the greatest psychological tests.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg")
                        .trailerUrl("https://www.youtube.com/embed/EXeTwQWrcwY")
                        .duration(152).releaseYear(2008).type(VideoType.FILM).category(Category.ACTION)
                        .rating(9.0).director("Christopher Nolan")
                        .cast(List.of("Christian Bale", "Heath Ledger", "Aaron Eckhart", "Michael Caine"))
                        .views(32100).featured(false).build(),

                Video.builder()
                        .title("Stranger Things")
                        .description("When a young boy disappears, his mother and friends must confront terrifying supernatural forces.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/49WJfeN0moxb9IPfGn8AIqMGskD.jpg")
                        .trailerUrl("https://www.youtube.com/embed/b9EkMc79ZSU")
                        .duration(51).releaseYear(2016).type(VideoType.SERIES).category(Category.SCIENCE_FICTION)
                        .rating(8.7).director("The Duffer Brothers")
                        .cast(List.of("Millie Bobby Brown", "Finn Wolfhard", "Winona Ryder", "David Harbour"))
                        .views(41200).featured(false).build(),

                Video.builder()
                        .title("Interstellar")
                        .description("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg")
                        .trailerUrl("https://www.youtube.com/embed/zSWdZVtXT7E")
                        .duration(169).releaseYear(2014).type(VideoType.FILM).category(Category.SCIENCE_FICTION)
                        .rating(8.6).director("Christopher Nolan")
                        .cast(List.of("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain", "Michael Caine"))
                        .views(27800).featured(false).build(),

                Video.builder()
                        .title("The Office")
                        .description("A mockumentary on a group of typical office workers, where the workday consists of ego clashes and inappropriate behavior.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/qWnJzyZhyy74gjpSjIXWmuk0ifX.jpg")
                        .trailerUrl("https://www.youtube.com/embed/LHOtME2DL4g")
                        .duration(22).releaseYear(2005).type(VideoType.SERIES).category(Category.COMEDY)
                        .rating(8.9).director("Greg Daniels")
                        .cast(List.of("Steve Carell", "Rainn Wilson", "John Krasinski", "Jenna Fischer"))
                        .views(38500).featured(false).build(),

                Video.builder()
                        .title("Our Planet")
                        .description("Documentary series focusing on the breadth of the diversity of habitats around the world.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/kZLBG7nLKBZSsWxYw0d9mDQ1cDG.jpg")
                        .trailerUrl("https://www.youtube.com/embed/aETNYyrqNYE")
                        .duration(50).releaseYear(2019).type(VideoType.DOCUMENTARY).category(Category.DOCUMENTARY)
                        .rating(9.3).director("Alastair Fothergill")
                        .cast(List.of("David Attenborough"))
                        .views(15600).featured(false).build(),

                Video.builder()
                        .title("Pulp Fiction")
                        .description("The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg")
                        .trailerUrl("https://www.youtube.com/embed/s7EdQ4FqbhY")
                        .duration(154).releaseYear(1994).type(VideoType.FILM).category(Category.THRILLER)
                        .rating(8.9).director("Quentin Tarantino")
                        .cast(List.of("John Travolta", "Uma Thurman", "Samuel L. Jackson", "Bruce Willis"))
                        .views(24300).featured(false).build(),

                Video.builder()
                        .title("Game of Thrones")
                        .description("Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg")
                        .trailerUrl("https://www.youtube.com/embed/KPLWWIOCOOQ")
                        .duration(57).releaseYear(2011).type(VideoType.SERIES).category(Category.DRAMA)
                        .rating(9.2).director("David Benioff")
                        .cast(List.of("Emilia Clarke", "Peter Dinklage", "Kit Harington", "Lena Headey"))
                        .views(52400).featured(false).build(),

                Video.builder()
                        .title("The Matrix")
                        .description("A computer hacker learns about the true nature of his reality and his role in the war against its controllers.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg")
                        .trailerUrl("https://www.youtube.com/embed/vKQi3bBA1y8")
                        .duration(136).releaseYear(1999).type(VideoType.FILM).category(Category.SCIENCE_FICTION)
                        .rating(8.7).director("The Wachowskis")
                        .cast(List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Anne Moss", "Hugo Weaving"))
                        .views(31200).featured(false).build(),

                Video.builder()
                        .title("The Social Dilemma")
                        .description("Explores the dangerous human impact of social networking, with tech experts sounding the alarm.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/kuCVPrncl8ueXx6HdnHkOEZbFw9.jpg")
                        .trailerUrl("https://www.youtube.com/embed/uaaC57tcci0")
                        .duration(94).releaseYear(2020).type(VideoType.DOCUMENTARY).category(Category.DOCUMENTARY)
                        .rating(7.6).director("Jeff Orlowski")
                        .cast(List.of("Tristan Harris", "Aza Raskin", "Justin Rosenstein"))
                        .views(18900).featured(false).build(),

                Video.builder()
                        .title("Fight Club")
                        .description("An insomniac office worker and a devil-may-care soap maker form an underground fight club.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg")
                        .trailerUrl("https://www.youtube.com/embed/qtRKdVHc-cE")
                        .duration(139).releaseYear(1999).type(VideoType.FILM).category(Category.DRAMA)
                        .rating(8.8).director("David Fincher")
                        .cast(List.of("Brad Pitt", "Edward Norton", "Helena Bonham Carter"))
                        .views(29700).featured(false).build(),

                Video.builder()
                        .title("The Crown")
                        .description("Follows the political rivalries and romance of Queen Elizabeth II's reign and the events that shaped the second half of the 20th century.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/ntOFwPnwEGrVmLOxdCTmQ5G0TMJ.jpg")
                        .trailerUrl("https://www.youtube.com/embed/JWtnJjn6ng0")
                        .duration(58).releaseYear(2016).type(VideoType.SERIES).category(Category.DRAMA)
                        .rating(8.6).director("Peter Morgan")
                        .cast(List.of("Claire Foy", "Olivia Colman", "Imelda Staunton", "Matt Smith"))
                        .views(21300).featured(false).build(),

                Video.builder()
                        .title("Parasite")
                        .description("Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg")
                        .trailerUrl("https://www.youtube.com/embed/5xH0HfJHsaY")
                        .duration(132).releaseYear(2019).type(VideoType.FILM).category(Category.THRILLER)
                        .rating(8.5).director("Bong Joon-ho")
                        .cast(List.of("Song Kang-ho", "Lee Sun-kyun", "Cho Yeo-jeong", "Choi Woo-shik"))
                        .views(26400).featured(false).build(),

                Video.builder()
                        .title("Black Mirror")
                        .description("An anthology series exploring a twisted, high-tech multiverse where humanity's greatest innovations collide with their darkest instincts.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/7PRddO7z7mcPi21nMbDl8B8bIXM.jpg")
                        .trailerUrl("https://www.youtube.com/embed/jDiYGjp5iFg")
                        .duration(60).releaseYear(2011).type(VideoType.SERIES).category(Category.SCIENCE_FICTION)
                        .rating(8.7).director("Charlie Brooker")
                        .cast(List.of("Daniel Kaluuya", "Bryce Dallas Howard", "Jon Hamm"))
                        .views(33800).featured(false).build(),

                Video.builder()
                        .title("The Shawshank Redemption")
                        .description("Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg")
                        .trailerUrl("https://www.youtube.com/embed/6hB3S9bIaco")
                        .duration(142).releaseYear(1994).type(VideoType.FILM).category(Category.DRAMA)
                        .rating(9.3).director("Frank Darabont")
                        .cast(List.of("Tim Robbins", "Morgan Freeman", "Bob Gunton"))
                        .views(42100).featured(false).build(),

                Video.builder()
                        .title("Avengers: Endgame")
                        .description("After the devastating events of Infinity War, the Avengers assemble once more to reverse Thanos' actions.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg")
                        .trailerUrl("https://www.youtube.com/embed/TcMBFSGVi1c")
                        .duration(181).releaseYear(2019).type(VideoType.FILM).category(Category.ACTION)
                        .rating(8.4).director("Anthony Russo")
                        .cast(List.of("Robert Downey Jr.", "Chris Evans", "Mark Ruffalo", "Scarlett Johansson"))
                        .views(58300).featured(false).build(),

                Video.builder()
                        .title("The Witcher")
                        .description("Geralt of Rivia, a solitary monster hunter, struggles to find his place in a world where people often prove more wicked than beasts.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/7vjaCdMw15FEbXyLQTVa04URsPm.jpg")
                        .trailerUrl("https://www.youtube.com/embed/ndl1W4ltcmg")
                        .duration(60).releaseYear(2019).type(VideoType.SERIES).category(Category.ADVENTURE)
                        .rating(8.2).director("Lauren Schmidt")
                        .cast(List.of("Henry Cavill", "Anya Chalotra", "Freya Allan"))
                        .views(35600).featured(false).build(),

                Video.builder()
                        .title("Joker")
                        .description("In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg")
                        .trailerUrl("https://www.youtube.com/embed/zAGVQLHvwOY")
                        .duration(122).releaseYear(2019).type(VideoType.FILM).category(Category.DRAMA)
                        .rating(8.4).director("Todd Phillips")
                        .cast(List.of("Joaquin Phoenix", "Robert De Niro", "Zazie Beetz"))
                        .views(39200).featured(false).build(),

                Video.builder()
                        .title("Cosmos: A Spacetime Odyssey")
                        .description("An exploration of our discovery of the laws of nature and coordinates in space and time.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/eHHXFk8xrR7XfLVZDxPFfsh44YF.jpg")
                        .trailerUrl("https://www.youtube.com/embed/XFFrldAfWZY")
                        .duration(44).releaseYear(2014).type(VideoType.DOCUMENTARY).category(Category.DOCUMENTARY)
                        .rating(9.1).director("Brannon Braga")
                        .cast(List.of("Neil deGrasse Tyson"))
                        .views(16700).featured(false).build(),

                Video.builder()
                        .title("Spider-Man: Into the Spider-Verse")
                        .description("Teen Miles Morales becomes the Spider-Man of his reality and crosses paths with counterparts from other dimensions.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg")
                        .trailerUrl("https://www.youtube.com/embed/g4Hbz2jLxvQ")
                        .duration(117).releaseYear(2018).type(VideoType.FILM).category(Category.ANIMATION)
                        .rating(8.4).director("Bob Persichetti")
                        .cast(List.of("Shameik Moore", "Jake Johnson", "Hailee Steinfeld"))
                        .views(28400).featured(false).build(),

                Video.builder()
                        .title("The Mandalorian")
                        .description("The travels of a lone bounty hunter in the outer reaches of the galaxy, far from the authority of the New Republic.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg")
                        .trailerUrl("https://www.youtube.com/embed/aOC8E8z_ifw")
                        .duration(40).releaseYear(2019).type(VideoType.SERIES).category(Category.SCIENCE_FICTION)
                        .rating(8.7).director("Jon Favreau")
                        .cast(List.of("Pedro Pascal", "Gina Carano", "Giancarlo Esposito"))
                        .views(44500).featured(false).build(),

                Video.builder()
                        .title("A Quiet Place")
                        .description("A family must live in silence to avoid mysterious creatures that hunt by sound.")
                        .thumbnailUrl("https://image.tmdb.org/t/p/w500/nAU74GmpUk7t5iklEp3bufwDq4n.jpg")
                        .trailerUrl("https://www.youtube.com/embed/WR7cc5t7tv8")
                        .duration(90).releaseYear(2018).type(VideoType.FILM).category(Category.HORROR)
                        .rating(7.5).director("John Krasinski")
                        .cast(List.of("Emily Blunt", "John Krasinski", "Millicent Simmonds"))
                        .views(19800).featured(false).build()
        );

        videoRepository.saveAll(videos);
    }
}
