using CompetitionCSh.repository;
using log4net.Config;

namespace CompetitionCSh;

internal class Program
{
    public static void Main(string[] args)
    {
        XmlConfigurator.Configure(new FileInfo("App.config"));
        Console.WriteLine("Hello World!");
        var repository = new ParticipantDbRepository();

        foreach (var participant in repository.findAll()) Console.WriteLine(participant.ToString());
        //IAthleticTestRepository athleticTestRepository = new AthleticTestDbRepository();

        //IOrganizerRepository organizerRepository = new OrganizerDbRepository();

        //  IEntryRepository entryRepository = new EntryDbRepository();
        //  Participant participant = new Participant("sergiu", "dsa", 6);
        //  participant.Id = 2;
        //  AthleticTest athleticTest = new AthleticTest(6, 8, 50);
        //  athleticTest.Id = 2;
        // entryRepository.save(new Entry(participant,athleticTest));

        //Console.WriteLine(athleticTestRepository.findAthleticTestByTestLengthAndAge(1000, 6, 8));
    }
}