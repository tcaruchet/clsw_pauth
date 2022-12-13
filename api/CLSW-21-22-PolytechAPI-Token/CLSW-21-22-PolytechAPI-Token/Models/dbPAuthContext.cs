using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

// Code scaffolded by EF Core assumes nullable reference types (NRTs) are not used or disabled.
// If you have enabled NRTs for your project, then un-comment the following line:
// #nullable disable

namespace CLSW_21_22_PolytechAPI_Token.Models
{
    public partial class dbPAuthContext : DbContext
    {
        public dbPAuthContext()
        {
        }

        public dbPAuthContext(DbContextOptions<dbPAuthContext> options)
            : base(options)
        {
        }

        public virtual DbSet<TbToken> TbToken { get; set; }

//        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
//        {
//            if (!optionsBuilder.IsConfigured)
//            {
//#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
//                optionsBuilder.UseSqlServer("server=\\\\MSSQL2019;database=dbPAuth;");
//            }
//        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<TbToken>(entity =>
            {
                entity.HasKey(e => e.Uid)
                    .HasName("PK__tbToken__C5B69A4ABE7349B1");

                entity.ToTable("tbToken");

                entity.Property(e => e.Uid).HasDefaultValueSql("(newid())");

                entity.Property(e => e.Bpm).HasColumnName("BPM");

                entity.Property(e => e.DateRequest)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("(getdate())");

                entity.Property(e => e.Token)
                    .IsRequired()
                    .HasMaxLength(10);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
