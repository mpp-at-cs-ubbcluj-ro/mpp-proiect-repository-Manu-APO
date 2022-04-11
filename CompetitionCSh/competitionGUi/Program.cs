using CompetitionCSh.repository;
using CompetitionCSh.repository.interfaces;
using CompetitionCSh.service;
using log4net.Config;

namespace competitionGUi
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            ApplicationConfiguration.Initialize();

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));

            IParticipantRepository participantDb = new ParticipantDbRepository();
            IRegistryRepository registryDb = new RegistryDbRepository();
            IParticipationRepository participationDb = new ParticipationDbRepository();
            ITrialRepository trialDb = new TrialDbRepository();

            CompetitionService competitionService =
                new CompetitionService(participantDb, trialDb, registryDb, participationDb);

            Application.Run(new Form1(competitionService));
        }
    }
}